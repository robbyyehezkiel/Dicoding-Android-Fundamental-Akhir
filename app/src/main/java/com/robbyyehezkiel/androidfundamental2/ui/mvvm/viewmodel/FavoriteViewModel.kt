package com.robbyyehezkiel.androidfundamental2.ui.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.robbyyehezkiel.androidfundamental2.data.model.User
import com.robbyyehezkiel.androidfundamental2.data.repository.FavoriteUserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel(private val repository: FavoriteUserRepository) : BaseViewModel() {

    private val _favoriteUsers = MutableLiveData<List<User>>()
    val favoriteUsers: LiveData<List<User>> = _favoriteUsers

    init {
        loadFavoriteUsers()
    }

    private fun loadFavoriteUsers() {
        setLoadingState(true)
        viewModelScope.launch(exceptionHandler) {
            try {
                val favoriteUsers = withContext(dispatcherIO) {
                    repository.getFavoriteUsers().map { user ->
                        User(
                            user.login,
                            user.avatarUrl,
                            user.name,
                            user.followers,
                            user.following
                        )
                    }
                }
                _favoriteUsers.value = favoriteUsers
                if (favoriteUsers.isEmpty()) {
                    showSnackBarMessage("No favorite users found.")
                }
            } catch (e: Exception) {
                handleException(e)
            } finally {
                setLoadingState(false)
            }
        }
    }
}
