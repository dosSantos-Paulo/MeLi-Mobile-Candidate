package com.dossantos.melimobilecandidate.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dossantos.designsystem.offer.MeLiOfferCardFragment.Companion.MeLiOffer
import com.dossantos.domain.offer.OfferEntity
import com.dossantos.domain.offer.OfferUseCase
import com.dossantos.melimobilecandidate.utils.Integers.three
import com.dossantos.melimobilecandidate.utils.Integers.zero
import com.dossantos.melimobilecandidate.utils.runOnIO
import com.dossantos.melimobilecandidate.utils.runOnMain
import com.dossantos.melimobilecandidate.utils.singleOrThrow

class HomeViewModel(
    private val offerUseCase: OfferUseCase
) : ViewModel() {

    private var offersRetry = zero
    private val _offerUiState = MutableLiveData<OfferUiState>()
    val offerUiState: LiveData<OfferUiState>
        get() = _offerUiState

    fun init() {
        if (_offerUiState.value?.uiState == null) getOffers()
    }

    private fun getOffers() = runOnMain {
        offerUseCase.getOffers().singleOrThrow(::onOfferSuccess, { onOfferError() })
    }

    fun retryOffer() {
        offersRetry++
        if (offersRetry <= maxRetry) getOffers()
    }

    private fun onOfferSuccess(offers: List<OfferEntity>) {
        _offerUiState.value = OfferUiState().onSuccess(offers.map {
            it.toMeLiOffer()
        })
    }

    private fun onOfferError() {
        _offerUiState.value = OfferUiState().onError()
    }

    companion object {
        const val maxRetry = three

        private fun OfferEntity.toMeLiOffer() = MeLiOffer(
            imageUrl = this.offerImageUrl,
            id = this.offerId,
            contentDescription = this.offerContentDescription
        )

        data class OfferUiState(val uiState: UiState? = null) {

            fun onSuccess(offers: List<MeLiOffer>) = copy(
                uiState = UiState.OnSuccess(offers)
            )

            fun onError() = copy(uiState = UiState.OnError)

            sealed interface UiState {
                data class OnSuccess(val offers: List<MeLiOffer>) : UiState
                data object OnError : UiState
            }
        }
    }
}
