package com.dossantos.melimobilecandidate.viewmodel.home

import com.dossantos.designsystem.model.offer.MeLiOffer

data class OfferUiState(val uiState: StateUi? = null) {

    fun onSuccess(offers: List<MeLiOffer>) = copy(
        uiState = StateUi.OnSuccess(offers)
    )

    fun onError() = copy(uiState = StateUi.OnError)

    sealed interface StateUi {
        data class OnSuccess(val offers: List<MeLiOffer>) : StateUi
        data object OnError : StateUi
    }
}