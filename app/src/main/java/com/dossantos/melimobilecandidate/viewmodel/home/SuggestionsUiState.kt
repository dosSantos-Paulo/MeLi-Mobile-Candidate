package com.dossantos.melimobilecandidate.viewmodel.home

import com.dossantos.designsystem.model.suggestions.MeLiProducts
import com.dossantos.domain.model.suggestions.SuggestionsType

data class SuggestionsUiState(val uiState: StateUi? = null) {

    fun onSuccess(categories: List<Pair<SuggestionsType, List<MeLiProducts>?>>) = copy(
        uiState = StateUi.OnSuccess(categories)
    )

    fun onError() = copy(uiState = StateUi.OnError)

    fun onLoading() = copy(uiState = StateUi.ShowLoading)

    sealed interface StateUi {
        data class OnSuccess(val suggestions: List<Pair<SuggestionsType, List<MeLiProducts>?>>) : StateUi
        data object OnError : StateUi
        data object ShowLoading: StateUi
    }
}
