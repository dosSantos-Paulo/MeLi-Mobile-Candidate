package com.dossantos.melimobilecandidate.viewmodel.product

import com.dossantos.designsystem.model.product.MeLiProductModel

data class ProductDetailStateUi(val uiState: StateUi? = null) {

    fun onSuccess(product: MeLiProductModel) = copy(
        uiState = StateUi.OnSuccess(product)
    )

    fun onError() = copy(uiState = StateUi.OnError)

    fun onLoading() = copy(uiState = StateUi.ShowLoading)

    sealed interface StateUi {
        data class OnSuccess(val product: MeLiProductModel) : StateUi
        data object OnError : StateUi
        data object ShowLoading: StateUi
    }
}
