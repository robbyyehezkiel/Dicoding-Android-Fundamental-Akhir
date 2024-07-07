package com.robbyyehezkiel.androidfundamental2.ui.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.robbyyehezkiel.androidfundamental2.data.model.FavoriteUser
import com.robbyyehezkiel.androidfundamental2.data.model.User
import com.robbyyehezkiel.androidfundamental2.data.repository.FavoriteUserRepository
import com.robbyyehezkiel.androidfundamental2.data.repository.GitHubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDetailViewModel(
    private val gitHubRepository: GitHubRepository,
    private val favoriteUserRepository: FavoriteUserRepository
) : BaseViewModel() {

    private val _userDetail = MutableLiveData<User>()
    val userDetail: LiveData<User> = _userDetail

    private val _isFavoriteUser = MutableLiveData<Boolean>()
    val isFavoriteUser: LiveData<Boolean> = _isFavoriteUser

    fun fetchUserDetail(username: String) {
        setLoadingState(true)
        viewModelScope.launch(exceptionHandler) {
            try {
                val userDetails = withContext(Dispatchers.IO) {
                    gitHubRepository.getUserDetail(username)
                }
                _userDetail.postValue(userDetails)
            } catch (e: Exception) {
                handleException(e)
            } finally {
                setLoadingState(false)
            }
        }
    }

    fun checkFavoriteUserStatus(username: String) {
        viewModelScope.launch {
            favoriteUserRepository.getFavoriteUsers()
                .collect { favoriteUsers ->
                    _isFavoriteUser.value = favoriteUsers.any { it.login == username }
                }
        }
    }

    fun addFavoriteUser(user: User) {
        viewModelScope.launch {
            val favoriteUser = FavoriteUser(
                login = user.login,
                avatarUrl = user.avatar_url,
                name = user.name,
                followers = user.followers,
                following = user.following
            )
            favoriteUserRepository.addFavoriteUser(favoriteUser)
            _isFavoriteUser.value = true
        }
    }

    fun removeFavoriteUser(user: User) {
        viewModelScope.launch {
            val favoriteUser = FavoriteUser(
                login = user.login,
                avatarUrl = user.avatar_url,
                name = user.name,
                followers = user.followers,
                following = user.following
            )
            favoriteUserRepository.removeFavoriteUser(favoriteUser)
            _isFavoriteUser.value = false
        }
    }
}
