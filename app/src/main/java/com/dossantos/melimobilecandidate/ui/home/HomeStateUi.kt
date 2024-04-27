package com.dossantos.melimobilecandidate.ui.home

import com.dossantos.melimobilecandidate.ui.base.StateUi

data class HomeStateUi(val currentState: UiState? = null) : StateUi() {
    fun onSuccess(message: String) = copy(
        currentState = UiState.OnSuccess(message)
    )

    override fun onError(message: String) = copy(
        currentState = UiState.OnError(message)
    )

    override fun showLoading() = copy(
        currentState = UiState.ShowLoading
    )

    sealed interface UiState {
        data class OnSuccess(val message: String) : UiState
        data class OnError(val message: String) : UiState
        data object ShowLoading : UiState
    }
}
