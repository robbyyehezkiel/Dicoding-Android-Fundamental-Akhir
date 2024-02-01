package com.robbyyehezkiel.androidfundamental2.ui.mvvm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.robbyyehezkiel.androidfundamental2.data.repository.GitHubUserRepository
import com.robbyyehezkiel.androidfundamental2.ui.mvvm.viewmodel.FollowViewModel
import com.robbyyehezkiel.androidfundamental2.ui.mvvm.viewmodel.MainViewModel

class ViewModelFactory(private val repository: GitHubUserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(FollowViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FollowViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}