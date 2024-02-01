package com.robbyyehezkiel.androidfundamental2.ui.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

open class BaseViewModel : ViewModel() {


    protected val isLoading = MutableLiveData<Boolean>()
    val isLoadingState: LiveData<Boolean> = isLoading

    protected val error = MutableLiveData<String>()
    val errorState: LiveData<String> = error

    protected val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        error.value = "Error: ${exception.message}"
    }
}