package com.robbyyehezkiel.androidfundamental2.ui.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robbyyehezkiel.androidfundamental2.data.model.User
import com.robbyyehezkiel.androidfundamental2.data.repository.FavoriteUserRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: FavoriteUserRepository) : ViewModel() {

    private val _favoriteUsers = MutableLiveData<List<User>>()
    val favoriteUsers: LiveData<List<User>> = _favoriteUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorState = MutableLiveData<String>()
    val errorState: LiveData<String> = _errorState

    init {
        loadFavoriteUsers()
    }

    private fun loadFavoriteUsers() {
        _isLoading.value = true
        viewModelScope.launch {
            repository.getFavoriteUsers()
                .catch { exception ->
                    _errorState.value = exception.message
                    _isLoading.value = false
                }
                .collect { favoriteUsersList ->
                    _favoriteUsers.value = favoriteUsersList.map { user ->
                        User(
                            user.login,
                            user.avatarUrl,
                            user.name,
                            user.followers,
                            user.following
                        )
                    }
                    _isLoading.value = false
                    if (favoriteUsersList.isEmpty()) {
                        _errorState.value = "No favorite users found."
                    }
                }
        }
    }
}
