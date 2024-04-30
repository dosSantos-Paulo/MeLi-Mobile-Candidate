package com.dossantos.melimobilecandidate.viewmodel.home

import com.dossantos.designsystem.model.suggestions.MeLiSuggestion
import com.dossantos.domain.model.suggestions.SuggestionsType

data class SuggestionsUiState(val uiState: StateUi? = null) {

    fun onSuccess(categories: List<Pair<SuggestionsType, List<MeLiSuggestion>?>>) = copy(
        uiState = StateUi.OnSuccess(categories)
    )

    fun onError() = copy(uiState = StateUi.OnError)

    sealed interface StateUi {
        data class OnSuccess(val suggestions: List<Pair<SuggestionsType, List<MeLiSuggestion>?>>) : StateUi
        data object OnError : StateUi
    }
}
