package com.robbyyehezkiel.androidfundamental2.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.robbyyehezkiel.androidfundamental2.R
import com.robbyyehezkiel.androidfundamental2.data.api.RetrofitService
import com.robbyyehezkiel.androidfundamental2.data.repository.GitHubUserRepository
import com.robbyyehezkiel.androidfundamental2.databinding.ActivityMainBinding
import com.robbyyehezkiel.androidfundamental2.ui.adapter.UserAdapter
import com.robbyyehezkiel.androidfundamental2.ui.mvvm.factory.ViewModelFactory
import com.robbyyehezkiel.androidfundamental2.ui.mvvm.viewmodel.MainViewModel
import com.robbyyehezkiel.androidfundamental2.utils.SnackBarUtil
import com.robbyyehezkiel.androidfundamental2.utils.ThemeManager
import com.robbyyehezkiel.androidfundamental2.utils.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyTheme()
        installSplashScreen()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setup() {
        setupViewModel()
        setupViews()
    }

    private fun setupViewModel() {
        val gitHubApi = RetrofitService.provideGitHubApi()
        val repository = GitHubUserRepository(gitHubApi)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(repository)
        )[MainViewModel::class.java]
        observeViewModel()
    }

    private fun setupViews() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter { user ->
            navigateToUserDetail(user.login)
        }
        binding.recyclerView.adapter = adapter

        binding.buttonSearch.setOnClickListener {
            searchUsers()
        }
    }

    private fun navigateToUserDetail(username: String) {
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra("USERNAME", username)
        startActivity(intent)
    }

    private fun searchUsers() {
        val query = binding.editTextQuery.text.toString().trim()
        if (query.isNotEmpty()) {
            viewModel.searchUsers(query)
        } else {
            viewModel.showEmptyQueryError()
        }
    }

    private fun observeViewModel() {
        viewModel.user.observe(this) { users ->
            adapter.submitList(users)
        }

        viewModel.errorState.observe(this) { error ->
            showErrorSnackBar(error)
        }

        viewModel.isLoadingState.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun showErrorSnackBar(message: String) {
        SnackBarUtil.showSnackBar(binding.root, message)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_menu -> navigateToFavoriteActivity()
            R.id.setting_menu -> navigateToThemeActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateToFavoriteActivity() {
        startActivity(Intent(this, FavoriteActivity::class.java))
    }

    private fun navigateToThemeActivity() {
        startActivity(Intent(this, SettingActivity::class.java))
    }

    private fun applyTheme() {
        val themeKey = stringPreferencesKey("theme")
        val themeFlow = dataStore.data.map { preferences ->
            preferences[themeKey] ?: "light"
        }

        lifecycleScope.launch {
            val themeMode = themeFlow.first()
            ThemeManager.applyTheme(themeMode)
        }
    }
}