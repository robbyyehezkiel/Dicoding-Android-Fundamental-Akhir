package com.robbyyehezkiel.androidfundamental2.ui.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.robbyyehezkiel.androidfundamental2.R
import com.robbyyehezkiel.androidfundamental2.data.api.RetrofitService
import com.robbyyehezkiel.androidfundamental2.data.database.AppDatabase
import com.robbyyehezkiel.androidfundamental2.data.repository.FavoriteUserRepository
import com.robbyyehezkiel.androidfundamental2.data.repository.GitHubUserRepository
import com.robbyyehezkiel.androidfundamental2.databinding.ActivityUserDetailBinding
import com.robbyyehezkiel.androidfundamental2.ui.mvvm.factory.FavoriteViewModelFactory
import com.robbyyehezkiel.androidfundamental2.ui.mvvm.viewmodel.UserDetailViewModel
import com.robbyyehezkiel.androidfundamental2.ui.view.fragments.FollowFragment
import com.robbyyehezkiel.androidfundamental2.utils.SnackBarUtil

class UserDetailActivity : AppCompatActivity() {
    private var _binding: ActivityUserDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UserDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = resources.getString(R.string.toolbar_user_detail)
            setDisplayHomeAsUpEnabled(true)
        }

        val username = intent.getStringExtra("USERNAME")
        if (username != null) {
            setupViewModel()
            viewModel.fetchUserDetail(username)
            observeViewModel()
            setupViewPager(username)
            setupFavoriteButton()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setupViewModel() {
        val gitHubApi = RetrofitService.provideGitHubApi()
        val repository = GitHubUserRepository(gitHubApi)
        val favoriteUserDao = AppDatabase.getInstance(applicationContext).favoriteUserDao()
        val favoriteUserRepository = FavoriteUserRepository(favoriteUserDao)
        val viewModelFactory = FavoriteViewModelFactory(repository, favoriteUserRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[UserDetailViewModel::class.java]
    }

    private fun observeViewModel() {
        viewModel.userDetail.observe(this) { userDetails ->
            binding.apply {
                Glide.with(this@UserDetailActivity)
                    .load(userDetails.avatar_url)
                    .into(imageAvatar)
                textUsername.text = userDetails.login
                textName.text = userDetails.name
                textFollowers.text = userDetails.followers.toString()
                textFollowing.text = userDetails.following.toString()
            }
            viewModel.checkFavoriteUserStatus(userDetails.login)
        }

        viewModel.errorState.observe(this) { error ->
            showErrorSnackBar(error)
        }

        viewModel.isLoadingState.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.isFavoriteUser.observe(this) { isFavorite ->
            updateFavoriteButton(isFavorite)
        }
    }

    private fun setupViewPager(username: String) {
        val pagerAdapter = UserDetailPagerAdapter(this, username)
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.following)
                1 -> getString(R.string.followers)
                else -> ""
            }
        }.attach()
    }

    private fun setupFavoriteButton() {
        binding.fab.setOnClickListener {
            val user = viewModel.userDetail.value
            if (user != null) {
                if (viewModel.isFavoriteUser.value == true) {
                    viewModel.removeFavoriteUser(user)
                } else {
                    viewModel.addFavoriteUser(user)
                }
            }
        }
    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        val drawableRes =
            if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_outline
        binding.fab.setImageResource(drawableRes)
    }

    private fun showErrorSnackBar(message: String) {
        SnackBarUtil.showSnackBar(binding.root, message)
    }

    private inner class UserDetailPagerAdapter(
        activity: AppCompatActivity,
        private val username: String
    ) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> FollowFragment.newInstance(username, isFollowing = true)
                1 -> FollowFragment.newInstance(username, isFollowing = false)
                else -> throw IllegalArgumentException("Invalid position: $position")
            }
        }
    }
}
