package com.dossantos.melimobilecandidate.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dossantos.designsystem.ui.category.MeLiCategoryCarousel.Companion.MeLiCategory
import com.dossantos.designsystem.ui.offer.MeLiOfferCardFragment.Companion.MeLiOffer
import com.dossantos.domain.model.category.MenuCategoryEntity
import com.dossantos.domain.model.offer.OfferEntity
import com.dossantos.domain.usecase.category.CategoryMenuUseCase
import com.dossantos.domain.usecase.offer.OfferUseCase
import com.dossantos.melimobilecandidate.utils.Integers.three
import com.dossantos.melimobilecandidate.utils.Integers.zero
import com.dossantos.melimobilecandidate.utils.runOnMain
import com.dossantos.melimobilecandidate.utils.singleOrThrow

class HomeViewModel(
    private val offerUseCase: OfferUseCase,
    private val categoryMenuUseCase: CategoryMenuUseCase
) : ViewModel() {

    private var offersRetry = zero
    private var categoryMenuRetry = zero

    private val _offerUiState = MutableLiveData<OfferUiState>()
    val offerUiState: LiveData<OfferUiState>
        get() = _offerUiState

    private val _categoryMenuUiState = MutableLiveData<CategoryMenuUiState>()
    val categoryMenuUiState: LiveData<CategoryMenuUiState>
        get() = _categoryMenuUiState

    fun init() {
        if (_offerUiState.value?.uiState == null) getOffers()
        if (_categoryMenuUiState.value?.uiState == null) getCategoryMenu()
    }

    private fun getOffers() = runOnMain {
        offerUseCase.getOffers()
            .singleOrThrow(::onOfferSuccess, { onOfferError() })
    }

    private fun getCategoryMenu() = runOnMain {
        categoryMenuUseCase.getMenuCategory()
            .singleOrThrow(::onCategoryMenuSuccess, { onCategoryMenuError() })
    }

    fun retryOffer() {
        offersRetry++
        if (offersRetry <= maxRetry) getOffers()
    }

    fun retryCategoryMenu() {
        categoryMenuRetry++
        if (categoryMenuRetry <= maxRetry) getCategoryMenu()
    }

    private fun onCategoryMenuSuccess(categoryMenu: List<MenuCategoryEntity>) {
        _categoryMenuUiState.value = CategoryMenuUiState().onSuccess(
            categoryMenu.map { it.toMeLiCategory() }
        )
    }

    private fun onOfferSuccess(offers: List<OfferEntity>) {
        _offerUiState.value = OfferUiState().onSuccess(
            offers.map { it.toMeLiOffer() }
        )
    }


    private fun onCategoryMenuError() {
        _categoryMenuUiState.value = CategoryMenuUiState().onError()
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

        private fun MenuCategoryEntity.toMeLiCategory() = MeLiCategory(
            name = this.categoryName,
            imageUrl = this.categoryImage,
            id = this.categoryId
        )

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

        data class CategoryMenuUiState(val uiState: StateUi? = null) {

            fun onSuccess(categories: List<MeLiCategory>) = copy(
                uiState = StateUi.OnSuccess(categories)
            )

            fun onError() = copy(uiState = StateUi.OnError)

            sealed interface StateUi {
                data class OnSuccess(val categories: List<MeLiCategory>) : StateUi
                data object OnError : StateUi
            }
        }
    }
}
