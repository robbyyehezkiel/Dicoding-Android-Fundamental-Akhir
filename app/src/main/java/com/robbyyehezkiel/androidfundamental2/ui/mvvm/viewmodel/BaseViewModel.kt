package com.robbyyehezkiel.androidfundamental2.ui.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers

open class BaseViewModel : ViewModel() {

    protected val dispatcherIO = Dispatchers.IO

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoadingState: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val errorState: LiveData<String> = _error

    protected val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("BaseViewModel", "Coroutine exception", exception)
        _error.postValue("Error: ${exception.message}")
        _isLoading.postValue(false)
    }

    protected fun handleException(exception: Throwable) {
        Log.e("BaseViewModel", "Exception handled: ${exception.message}", exception)
        _error.postValue("Error: ${exception.message}")
        _isLoading.postValue(false)
    }

    protected fun showSnackBarMessage(message: String) {
        _error.postValue(message)
    }

    protected fun setLoadingState(isLoading: Boolean) {
        _isLoading.postValue(isLoading)
    }
}
