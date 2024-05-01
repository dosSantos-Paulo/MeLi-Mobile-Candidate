package com.dossantos.melimobilecandidate.viewmodel.search

import com.dossantos.designsystem.model.suggestions.MeLiProducts

data class SearchStateUi(val uiState: StateUi? = null) {

    fun onSuccess(categories: List<MeLiProducts>) = copy(
        uiState = StateUi.OnSuccess(categories)
    )

    fun onError() = copy(uiState = StateUi.OnError)

    fun onLoading() = copy(uiState = StateUi.ShowLoading)

    sealed interface StateUi {
        data class OnSuccess(val products: List<MeLiProducts>) : StateUi
        data object OnError : StateUi
        data object ShowLoading: StateUi
    }
}
