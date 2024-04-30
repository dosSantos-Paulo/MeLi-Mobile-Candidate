package com.dossantos.melimobilecandidate.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dossantos.designsystem.model.category.MeLiCategory
import com.dossantos.designsystem.model.offer.MeLiOffer
import com.dossantos.designsystem.model.suggestions.MeLiSuggestion
import com.dossantos.domain.model.category.MenuCategoryModel
import com.dossantos.domain.model.offer.OfferModel
import com.dossantos.domain.model.search.ProductsModel
import com.dossantos.domain.model.suggestions.SuggestionsModel
import com.dossantos.domain.model.suggestions.SuggestionsType
import com.dossantos.domain.usecase.category.CategoryMenuUseCase
import com.dossantos.domain.usecase.offer.OfferUseCase
import com.dossantos.domain.usecase.suggestions.SuggestionsBusiness
import com.dossantos.melimobilecandidate.utils.Integers.three
import com.dossantos.melimobilecandidate.utils.Integers.zero
import com.dossantos.melimobilecandidate.utils.runOnIO
import com.dossantos.melimobilecandidate.utils.runOnMain
import com.dossantos.melimobilecandidate.utils.singleOrThrow

class HomeViewModel(
    private val offerUseCase: OfferUseCase,
    private val categoryMenuUseCase: CategoryMenuUseCase,
    private val suggestionsBusiness: SuggestionsBusiness
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
        offerUseCase.getOffers()
            .singleOrThrow(::onOfferSuccess, ::onOfferError)
    }

    private fun getCategoryMenu() = runOnMain {
        categoryMenuUseCase.getMenuCategory()
            .singleOrThrow(::onCategoryMenuSuccess, ::onCategoryMenuError)
    }

    private fun getSuggestions() = runOnIO {
        suggestionsBusiness.getSuggestions()
            .singleOrThrow(::onSuggestionsSuccess, ::onSuggestionsError)
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
                suggestions.toMeLiSuggestion()
            )
        )
    }

    private fun onSuggestionsError(ex: Exception) {
        ex.printStackTrace()
        _suggestionsUiState.postValue(SuggestionsUiState().onError())
    }

    private fun onCategoryMenuError(ex: Exception) {
        ex.printStackTrace()
        _categoryMenuUiState.postValue(CategoryMenuUiState().onError())
    }

    private fun onOfferError(ex: Exception) {
        ex.printStackTrace()
        _offerUiState.postValue(OfferUiState().onError())
    }

    private fun OfferModel.toMeLiOffer() = MeLiOffer(
        imageUrl = this.offerImageUrl,
        id = this.offerId,
        contentDescription = this.offerContentDescription
    )

    private fun MenuCategoryModel.toMeLiCategory() = MeLiCategory(
        name = this.categoryName,
        imageUrl = this.categoryImage,
        id = this.categoryId
    )

    private fun List<SuggestionsModel?>.toMeLiSuggestion(): List<Pair<SuggestionsType, List<MeLiSuggestion>?>> {
        val list = mutableListOf<Pair<SuggestionsType, List<MeLiSuggestion>?>>()
        forEach { suggestions ->
            suggestions?.run {
                list.add(suggestionsType to products?.map { product -> product.toMeLiSuggestion() })
            }
        }
        return list.toList()
    }

    private fun ProductsModel.toMeLiSuggestion() = MeLiSuggestion(
        itemId = id,
        title = title,
        lastPrice = originalPrice,
        discountPercentage = itemDiscount,
        price = price,
        imageUrl = thumbnail
    )
}
