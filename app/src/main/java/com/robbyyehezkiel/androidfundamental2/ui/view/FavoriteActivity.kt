package com.robbyyehezkiel.androidfundamental2.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.robbyyehezkiel.androidfundamental2.R
import com.robbyyehezkiel.androidfundamental2.data.api.RetrofitService
import com.robbyyehezkiel.androidfundamental2.data.database.AppDatabase
import com.robbyyehezkiel.androidfundamental2.data.repository.FavoriteUserRepository
import com.robbyyehezkiel.androidfundamental2.data.repository.GitHubUserRepository
import com.robbyyehezkiel.androidfundamental2.databinding.ActivityFavoriteBinding
import com.robbyyehezkiel.androidfundamental2.ui.adapter.UserAdapter
import com.robbyyehezkiel.androidfundamental2.ui.mvvm.factory.FavoriteViewModelFactory
import com.robbyyehezkiel.androidfundamental2.ui.mvvm.viewmodel.FavoriteViewModel
import com.robbyyehezkiel.androidfundamental2.utils.SnackBarUtil

class FavoriteActivity : AppCompatActivity() {

    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var userAdapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = getString(R.string.toolbar_favorite_user)
            setDisplayHomeAsUpEnabled(true)
        }
        setupViewModel()
        observeViewModel()
        setupRecyclerView()
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
        val gitHubRepository = GitHubUserRepository(RetrofitService.provideGitHubApi())
        val favoriteUserRepository = FavoriteUserRepository(
            AppDatabase.getInstance(applicationContext).favoriteUserDao()
        )
        val viewModelFactory = FavoriteViewModelFactory(gitHubRepository, favoriteUserRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[FavoriteViewModel::class.java]
    }

    private fun observeViewModel() {
        viewModel.favoriteUsers.observe(this) { users ->
            userAdapter.submitList(users)
        }
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorState.observe(this) { error ->
            showErrorSnackBar(error)
        }
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter { user ->
            navigateToUserDetail(user.login)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = userAdapter
        }
    }

    private fun navigateToUserDetail(username: String) {
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra("USERNAME", username)
        startActivity(intent)
    }

    private fun showErrorSnackBar(message: String) {
        SnackBarUtil.showSnackBar(binding.root, message)
    }
}
