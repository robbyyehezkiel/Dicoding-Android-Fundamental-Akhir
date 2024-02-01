package com.robbyyehezkiel.androidfundamental2.ui.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.robbyyehezkiel.androidfundamental2.data.model.User
import com.robbyyehezkiel.androidfundamental2.data.repository.GitHubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FollowViewModel(private val repository: GitHubRepository) : BaseViewModel() {

    private val _user = MutableLiveData<List<User>>()
    val user: LiveData<List<User>> = _user

    private val _followersError = MutableLiveData<Boolean>()
    val followersError: LiveData<Boolean> = _followersError

    private val _followingError = MutableLiveData<Boolean>()
    val followingError: LiveData<Boolean> = _followingError

    fun getUserFollowing(username: String) {
        isLoading.value = true
        viewModelScope.launch(exceptionHandler) {
            val userFollowing = withContext(Dispatchers.IO) {
                repository.getUserFollowing(username)
            }
            _user.value = userFollowing
            _followingError.value = userFollowing.isEmpty()
        }.invokeOnCompletion {
            isLoading.value = false
        }
    }

    fun getUserFollowers(username: String) {
        isLoading.value = true
        viewModelScope.launch(exceptionHandler) {
            val userFollowers = withContext(Dispatchers.IO) {
                repository.getUserFollowers(username)
            }
            _user.value = userFollowers
            _followersError.value = userFollowers.isEmpty()
        }.invokeOnCompletion {
            isLoading.value = false
        }
    }
}