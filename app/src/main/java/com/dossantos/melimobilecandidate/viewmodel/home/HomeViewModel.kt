package com.dossantos.melimobilecandidate.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dossantos.domain.model.category.MenuCategoryModel
import com.dossantos.domain.model.offer.OfferModel
import com.dossantos.domain.model.suggestions.SuggestionsModel
import com.dossantos.domain.usecase.category.CategoryMenuUseCase
import com.dossantos.domain.usecase.offer.OfferUseCase
import com.dossantos.domain.usecase.suggestions.SuggestionsUseCase
import com.dossantos.melimobilecandidate.model.home.toMeLiCategory
import com.dossantos.melimobilecandidate.model.home.toMeLiOffer
import com.dossantos.melimobilecandidate.model.home.toMeLiProduct
import com.dossantos.melimobilecandidate.utils.Integers.three
import com.dossantos.melimobilecandidate.utils.Integers.zero
import com.dossantos.melimobilecandidate.utils.runOnIO
import com.dossantos.melimobilecandidate.utils.singleOrThrow
import timber.log.Timber

class HomeViewModel(
    private val offerUseCase: OfferUseCase,
    private val categoryMenuUseCase: CategoryMenuUseCase,
    private val suggestionsUseCase: SuggestionsUseCase
) : ViewModel() {

    private val maxRetry = three
    private var offersRetry = zero
    private var categoryMenuRetry = zero
    private var suggestionsRetry = zero

    private val _offerUiState = MutableLiveData<OfferUiState>()
    val offerUiState: LiveData<OfferUiState>
        get() = _offerUiState

    private val _categoryMenuUiState = MutableLiveData<CategoryMenuUiState>()
    val categoryMenuUiState: LiveData<CategoryMenuUiState>
        get() = _categoryMenuUiState

    private val _suggestionsUiState = MutableLiveData<SuggestionsUiState>()
    val suggestionsUiState: LiveData<SuggestionsUiState>
        get() = _suggestionsUiState

    fun init() {
        if (_offerUiState.value?.uiState == null) getOffers()
        if (_categoryMenuUiState.value?.uiState == null) getCategoryMenu()
        if (_suggestionsUiState.value?.uiState == null) getSuggestions()
    }

    private fun getOffers() = runOnIO {
        _offerUiState.postValue(OfferUiState().onLoading())
        offerUseCase.getOffers()
            .singleOrThrow(::onOfferSuccess) { ex ->
                Timber.e(ex)
                _offerUiState.postValue(OfferUiState().onError())
            }
    }

    private fun getCategoryMenu() = runOnIO {
        _categoryMenuUiState.postValue(CategoryMenuUiState().onLoading())
        categoryMenuUseCase.getMenuCategory()
            .singleOrThrow(::onCategoryMenuSuccess) { ex ->
                Timber.e(ex)
                _categoryMenuUiState.postValue(CategoryMenuUiState().onError())
            }
    }

    private fun getSuggestions() = runOnIO {
        _suggestionsUiState.postValue(SuggestionsUiState().onLoading())
        suggestionsUseCase.getSuggestions()
            .singleOrThrow(::onSuggestionsSuccess) { ex ->
                Timber.e(ex)
                _suggestionsUiState.postValue(SuggestionsUiState().onError())
            }
    }

    fun retryOffer() {
        offersRetry++
        if (offersRetry <= maxRetry) getOffers()
    }

    fun retryCategoryMenu() {
        categoryMenuRetry++
        if (categoryMenuRetry <= maxRetry) getCategoryMenu()
    }

    fun retrySuggestions() {
        suggestionsRetry++
        if (suggestionsRetry <= maxRetry) getSuggestions()
    }

    private fun onOfferSuccess(offers: List<OfferModel>) {
        _offerUiState.postValue(OfferUiState().onSuccess(
            offers.map { it.toMeLiOffer() }
        ))
    }

    private fun onCategoryMenuSuccess(categoryMenu: List<MenuCategoryModel>) {
        _categoryMenuUiState.postValue(CategoryMenuUiState().onSuccess(
            categoryMenu.map { it.toMeLiCategory() }
        ))
    }

    private fun onSuggestionsSuccess(suggestions: List<SuggestionsModel?>) {
        _suggestionsUiState.postValue(
            SuggestionsUiState().onSuccess(
                suggestions.toMeLiProduct()
            )
        )
    }
}
