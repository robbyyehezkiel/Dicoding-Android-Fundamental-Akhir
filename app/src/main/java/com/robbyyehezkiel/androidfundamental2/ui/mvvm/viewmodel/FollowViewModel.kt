package com.robbyyehezkiel.androidfundamental2.ui.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.robbyyehezkiel.androidfundamental2.data.model.User
import com.robbyyehezkiel.androidfundamental2.data.repository.GitHubRepository
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
        setLoadingState(true)
        viewModelScope.launch(exceptionHandler) {
            try {
                val userFollowing = withContext(dispatcherIO) {
                    repository.getUserFollowing(username)
                }
                _user.postValue(userFollowing)
                if (userFollowing.isEmpty()) {
                    _followingError.postValue(true)
                    showSnackBarMessage("No following users found.")
                }
            } catch (e: Exception) {
                handleException(e)
            } finally {
                setLoadingState(false)
            }
        }
    }

    fun getUserFollowers(username: String) {
        setLoadingState(true)
        viewModelScope.launch(exceptionHandler) {
            try {
                val userFollowers = withContext(dispatcherIO) {
                    repository.getUserFollowers(username)
                }
                _user.postValue(userFollowers)
                if (userFollowers.isEmpty()) {
                    _followersError.postValue(true)
                    showSnackBarMessage("No followers found.")
                }
            } catch (e: Exception) {
                handleException(e)
            } finally {
                setLoadingState(false)
            }
        }
    }
}
