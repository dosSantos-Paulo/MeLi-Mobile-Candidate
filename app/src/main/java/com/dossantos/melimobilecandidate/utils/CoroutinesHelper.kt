package com.dossantos.melimobilecandidate.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

@SuppressWarnings("TooGenericExceptionCaught")
suspend fun <T> Flow<T>.singleOrThrow(
    success: ((T) -> Unit),
    error: ((Exception) -> Unit)? = null
) {
    try {
        success.invoke(single())
    } catch (exception: Exception) {
        /**
         * Detekt points out that this catch is too
         * generic. But the class is generic and needs the
         * Exception to be open to any possibility
         */
        error?.invoke(exception)
    }
}

fun ViewModel.runOnMain(scope: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch(context = Dispatchers.Main, block = scope)
}

fun ViewModel.runOnIO(scope: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch(context = Dispatchers.IO, block = scope)
}
