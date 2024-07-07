package com.robbyyehezkiel.androidfundamental2.ui.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.robbyyehezkiel.androidfundamental2.data.api.RetrofitService
import com.robbyyehezkiel.androidfundamental2.data.repository.GitHubUserRepository
import com.robbyyehezkiel.androidfundamental2.databinding.FragmentFollowBinding
import com.robbyyehezkiel.androidfundamental2.ui.adapter.UserAdapter
import com.robbyyehezkiel.androidfundamental2.ui.mvvm.factory.ViewModelFactory
import com.robbyyehezkiel.androidfundamental2.ui.mvvm.viewmodel.FollowViewModel
import com.robbyyehezkiel.androidfundamental2.ui.view.UserDetailActivity
import com.robbyyehezkiel.androidfundamental2.utils.SnackBarUtil

class FollowFragment(private val isFollowing: Boolean) : Fragment() {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private var username: String? = null
    private lateinit var viewModel: FollowViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            username = it.getString("USERNAME")
        }

        if (username.isNullOrEmpty()) {
            showErrorSnackBar("Username is null")
        } else {
            setupViews()
            setupViewModel()
            username?.let {
                if (isFollowing) {
                    viewModel.getUserFollowing(it)
                } else {
                    viewModel.getUserFollowers(it)
                }
            }
            observeViewModel()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViews() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = UserAdapter { user ->
            val intent = Intent(requireContext(), UserDetailActivity::class.java).apply {
                putExtra("USERNAME", user.login)
            }
            startActivity(intent)
        }
        binding.recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        val gitHubApi = RetrofitService.provideGitHubApi()
        val repository = GitHubUserRepository(gitHubApi)
        val viewModelFactory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[FollowViewModel::class.java]
    }

    private fun observeViewModel() {
        viewModel.apply {
            user.observe(viewLifecycleOwner) { users ->
                adapter.submitList(users)
            }

            followersError.observe(viewLifecycleOwner) { isError ->
                if (isError) {
                    showErrorSnackBar("Followers is null or empty")
                }
            }

            followingError.observe(viewLifecycleOwner) { isError ->
                if (isError) {
                    showErrorSnackBar("Following is null or empty")
                }
            }

            errorState.observe(viewLifecycleOwner) { error ->
                error?.let { showErrorSnackBar(it) }
            }

            isLoadingState.observe(viewLifecycleOwner) { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    private fun showErrorSnackBar(message: String) {
        SnackBarUtil.showSnackBar(binding.root, message)
    }

    companion object {
        fun newInstance(username: String, isFollowing: Boolean) =
            FollowFragment(isFollowing).apply {
                arguments = Bundle().apply {
                    putString("USERNAME", username)
                }
            }
    }
}
