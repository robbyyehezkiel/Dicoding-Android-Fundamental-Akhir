package com.robbyyehezkiel.androidfundamental2.ui.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.robbyyehezkiel.androidfundamental2.data.model.User
import com.robbyyehezkiel.androidfundamental2.data.repository.GitHubRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter

@OptIn(FlowPreview::class)
class MainViewModel(private val repository: GitHubRepository) : BaseViewModel() {

    private val _user = MutableLiveData<List<User>>()
    val user: LiveData<List<User>> = _user

    private val queryFlow = MutableStateFlow("")

    init {
        viewModelScope.launch {
            queryFlow
                .debounce(300)
                .filter { it.isNotEmpty() }
                .collectLatest { query ->
                    searchUsersInternal(query)
                }
        }
    }

    fun searchUsers(query: String) {
        queryFlow.value = query
    }

    private suspend fun searchUsersInternal(query: String) {
        setLoadingState(true)
        try {
            val users = withContext(Dispatchers.IO) {
                repository.searchUsers(query)
            }
            if (_user.value != users.items) {
                _user.postValue(users.items)
            }
            if (users.items.isEmpty()) {
                showSnackBarMessage("No users found.")
            }
        } catch (e: Exception) {
            handleException(e)
        } finally {
            setLoadingState(false)
        }
    }

    fun showEmptyQueryError() {
        showSnackBarMessage("Please enter a search query")
    }
}
