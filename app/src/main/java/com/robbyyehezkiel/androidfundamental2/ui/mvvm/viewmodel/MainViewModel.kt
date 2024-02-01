package com.robbyyehezkiel.androidfundamental2.ui.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.robbyyehezkiel.androidfundamental2.data.model.User
import com.robbyyehezkiel.androidfundamental2.data.repository.GitHubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val repository: GitHubRepository) : BaseViewModel() {

    private val _user = MutableLiveData<List<User>>()
    val user: LiveData<List<User>> = _user

    fun searchUsers(query: String) {
        isLoading.value = true
        viewModelScope.launch(exceptionHandler) {
            val user = withContext(Dispatchers.IO) {
                repository.searchUsers(query)
            }
            _user.value = user.items
        }.invokeOnCompletion {
            isLoading.value = false
        }
    }

    fun showEmptyQueryError() {
        error.value = "Please enter a search query"
    }
}