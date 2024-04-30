package com.dossantos.melimobilecandidate.viewmodel.home

import com.dossantos.designsystem.model.category.MeLiCategory

data class CategoryMenuUiState(val uiState: StateUi? = null) {

    fun onSuccess(categories: List<MeLiCategory>) = copy(
        uiState = StateUi.OnSuccess(categories)
    )

    fun onError() = copy(uiState = StateUi.OnError)

    fun onLoading() = copy(uiState = StateUi.ShowLoading)

    sealed interface StateUi {
        data class OnSuccess(val categories: List<MeLiCategory>) : StateUi
        data object OnError : StateUi
        data object ShowLoading: StateUi
    }
}
