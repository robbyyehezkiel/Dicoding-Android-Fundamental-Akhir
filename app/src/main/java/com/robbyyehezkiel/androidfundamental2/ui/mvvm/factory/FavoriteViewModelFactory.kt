package com.robbyyehezkiel.androidfundamental2.ui.mvvm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.robbyyehezkiel.androidfundamental2.data.repository.FavoriteUserRepository
import com.robbyyehezkiel.androidfundamental2.data.repository.GitHubUserRepository
import com.robbyyehezkiel.androidfundamental2.ui.mvvm.viewmodel.FavoriteViewModel
import com.robbyyehezkiel.androidfundamental2.ui.mvvm.viewmodel.UserDetailViewModel

class FavoriteViewModelFactory(
    private val repository: GitHubUserRepository,
    private val favoriteUserRepository: FavoriteUserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserDetailViewModel(repository, favoriteUserRepository) as T
        }
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(favoriteUserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}