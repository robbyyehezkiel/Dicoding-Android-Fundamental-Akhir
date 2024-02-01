package com.robbyyehezkiel.androidfundamental2.ui.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.robbyyehezkiel.androidfundamental2.data.model.User
import com.robbyyehezkiel.androidfundamental2.data.repository.FavoriteUserRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: FavoriteUserRepository) : BaseViewModel() {

    private val _favoriteUsers = MutableLiveData<List<User>>()
    val favoriteUsers: LiveData<List<User>> = _favoriteUsers

    init {
        loadFavoriteUsers()
    }

    private fun loadFavoriteUsers() {
        isLoading.value = true
        viewModelScope.launch(exceptionHandler) {
            val favoriteUsers = repository.getFavoriteUsers().map { user ->
                User(
                    user.login,
                    user.avatarUrl,
                    user.name,
                    user.followers,
                    user.following
                )
            }
            _favoriteUsers.value = favoriteUsers
        }.invokeOnCompletion {
            isLoading.value = false
        }
    }
}