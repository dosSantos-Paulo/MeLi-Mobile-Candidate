package com.dossantos.melimobilecandidate.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel<T : StateUi> : ViewModel() {

    abstract val mutableStateUi:MutableLiveData<T>
    val stateUi: LiveData<T>
        get() = mutableStateUi

    abstract fun init()

    protected fun getState() = stateUi.value as T

    protected fun runOnMain(scope: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(context = Dispatchers.Main, block = scope)
    }

    protected fun runOnIO(scope: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(context = Dispatchers.IO, block = scope)
    }

    protected fun updateState(newStateUi: T) {
        mutableStateUi.value = newStateUi
    }
}
