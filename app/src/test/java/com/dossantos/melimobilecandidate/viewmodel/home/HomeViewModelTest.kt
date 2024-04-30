package com.dossantos.melimobilecandidate.viewmodel.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.dossantos.domain.model.category.MenuCategoryModel
import com.dossantos.domain.model.offer.OfferModel
import com.dossantos.domain.model.suggestions.SuggestionsModel
import com.dossantos.domain.model.suggestions.SuggestionsType
import com.dossantos.domain.usecase.category.CategoryMenuUseCase
import com.dossantos.domain.usecase.offer.OfferUseCase
import com.dossantos.domain.usecase.suggestions.SuggestionsUseCase
import com.dossantos.melimobilecandidate.TestException
import com.dossantos.melimobilecandidate.getValue
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val offerUseCase: OfferUseCase = mockk()
    private val categoryMenuUseCase: CategoryMenuUseCase = mockk()
    private val suggestionsUseCase: SuggestionsUseCase = mockk()
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.IO)
        viewModel = HomeViewModel(offerUseCase, categoryMenuUseCase, suggestionsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `test getOffers with success`() = runBlocking {
        // Given
        coEvery { offerUseCase.getOffers() } returns getListOffers()
        coEvery { categoryMenuUseCase.getMenuCategory() } returns getListOfCategories()
        coEvery { suggestionsUseCase.getSuggestions() } returns getListOfSuggestions()

        // When
        viewModel.init()
        val obserable = getValue(viewModel.offerUiState)

        // Then
        assert(obserable?.uiState is OfferUiState.StateUi.OnSuccess)
    }

    @Test
    fun `test getOffers with error`() = runBlocking {
        // Given
        coEvery { offerUseCase.getOffers() } returns getListOffersThrow()
        coEvery { categoryMenuUseCase.getMenuCategory() } returns getListOfCategories()
        coEvery { suggestionsUseCase.getSuggestions() } returns getListOfSuggestions()

        // When
        viewModel.init()
        val obserable = getValue(viewModel.offerUiState)

        // Then
        assert(obserable?.uiState is OfferUiState.StateUi.OnError)
    }

    @Test
    fun `test getMenuCategory with success`() = runBlocking {
        // Given
        coEvery { offerUseCase.getOffers() } returns getListOffers()
        coEvery { categoryMenuUseCase.getMenuCategory() } returns getListOfCategories()
        coEvery { suggestionsUseCase.getSuggestions() } returns getListOfSuggestions()

        // When
        viewModel.init()
        val obserable = getValue(viewModel.categoryMenuUiState)

        // Then
        assert(obserable?.uiState is CategoryMenuUiState.StateUi.OnSuccess)
    }

    @Test
    fun `test getMenuCategory with error`() = runBlocking {
        // Given
        coEvery { offerUseCase.getOffers() } returns getListOffers()
        coEvery { categoryMenuUseCase.getMenuCategory() } returns getListOfCategoriesThrow()
        coEvery { suggestionsUseCase.getSuggestions() } returns getListOfSuggestions()

        // When
        viewModel.init()
        val obserable = getValue(viewModel.categoryMenuUiState)

        // Then
        assert(obserable?.uiState is CategoryMenuUiState.StateUi.OnError)
    }

    @Test
    fun `test getSuggestions with success`() = runBlocking {
        // Given
        coEvery { offerUseCase.getOffers() } returns getListOffers()
        coEvery { categoryMenuUseCase.getMenuCategory() } returns getListOfCategories()
        coEvery { suggestionsUseCase.getSuggestions() } returns getListOfSuggestions()

        // When
        viewModel.init()
        val obserable = getValue(viewModel.suggestionsUiState)

        // Then
        assert(obserable?.uiState is SuggestionsUiState.StateUi.OnSuccess)
    }

    @Test
    fun `test getSuggestions with error`() = runBlocking {
        // Given
        coEvery { offerUseCase.getOffers() } returns getListOffers()
        coEvery { categoryMenuUseCase.getMenuCategory() } returns getListOfCategories()
        coEvery { suggestionsUseCase.getSuggestions() } returns getListOfSuggestionsThrow()

        // When
        viewModel.init()
        val obserable = getValue(viewModel.suggestionsUiState)

        // Then
        assert(obserable?.uiState is SuggestionsUiState.StateUi.OnError)
    }

    @Test
    fun `test getOffers retry with success`() = runBlocking {
        // Given
        coEvery { offerUseCase.getOffers() } returns getListOffers()

        // When
        viewModel.retryOffer()
        val obserable = getValue(viewModel.offerUiState)

        // Then
        assert(obserable?.uiState is OfferUiState.StateUi.OnSuccess)
    }

    @Test
    fun `test getOffers retry with error`() = runBlocking {
        // Given
        coEvery { offerUseCase.getOffers() } returns getListOffersThrow()
        coEvery { categoryMenuUseCase.getMenuCategory() } returns getListOfCategories()
        coEvery { suggestionsUseCase.getSuggestions() } returns getListOfSuggestions()

        // When
        viewModel.init()
        recursiveOfferObservation(viewModel.offerUiState) { state ->
            viewModel.retryOffer()
        }

        // Then
        coVerify(exactly = 4) { offerUseCase.getOffers() }
    }

    private fun CoroutineScope.recursiveOfferObservation(
        liveData: LiveData<OfferUiState>,
        count: Int = 0,
        onContinue: (OfferUiState?) -> Unit
    ) {
        getValue(liveData)?.let { value ->
            if (value.uiState is OfferUiState.StateUi.OnError && count < 10) {
                onContinue(value)
                recursiveOfferObservation(liveData, count + 1, onContinue)
            }
        }
    }

    @Test
    fun `test category retry with success`() = runBlocking {
        // Given
        coEvery { categoryMenuUseCase.getMenuCategory() } returns getListOfCategories()

        // When
        viewModel.retryCategoryMenu()
        val observable = getValue(viewModel.categoryMenuUiState)

        // Then
        coVerify { categoryMenuUseCase.getMenuCategory() }
        assert(observable?.uiState is CategoryMenuUiState.StateUi.OnSuccess)
    }

    @Test
    fun `test category retry with error`() = runBlocking {
        // Given
        coEvery { offerUseCase.getOffers() } returns getListOffers()
        coEvery { categoryMenuUseCase.getMenuCategory() } returns getListOfCategoriesThrow()
        coEvery { suggestionsUseCase.getSuggestions() } returns getListOfSuggestions()

        // When
        viewModel.init()
        recursiveCategoryObservation(viewModel.categoryMenuUiState) {
            viewModel.retryCategoryMenu()
        }

        // Then
        coVerify(exactly = 4) { categoryMenuUseCase.getMenuCategory() }
    }

    private fun CoroutineScope.recursiveCategoryObservation(
        liveData: LiveData<CategoryMenuUiState>,
        count: Int = 0,
        onContinue: (CategoryMenuUiState?) -> Unit
    ) {
        getValue(liveData)?.let { value ->
            if (value.uiState is CategoryMenuUiState.StateUi.OnError && count < 10) {
                onContinue(value)
                recursiveCategoryObservation(liveData, count + 1, onContinue)
            }
        }
    }

    @Test
    fun `test suggestions retry with success`() = runBlocking {
        // Given
        coEvery { suggestionsUseCase.getSuggestions() } returns getListOfSuggestions()

        // When
        viewModel.retrySuggestions()
        val observable = getValue(viewModel.suggestionsUiState)

        // Then
        coVerify { suggestionsUseCase.getSuggestions() }
        assert(observable?.uiState is SuggestionsUiState.StateUi.OnSuccess)
    }

    @Test
    fun `test suggestions retry with error`() = runBlocking {
        // Given
        coEvery { offerUseCase.getOffers() } returns getListOffers()
        coEvery { categoryMenuUseCase.getMenuCategory() } returns getListOfCategories()
        coEvery { suggestionsUseCase.getSuggestions() } returns getListOfSuggestionsThrow()

        // When
        viewModel.init()
        recursiveSuggestionsObservation(viewModel.suggestionsUiState) {
            viewModel.retrySuggestions()
        }

        // Then
        coVerify(exactly = 4) { suggestionsUseCase.getSuggestions() }
    }

    private fun CoroutineScope.recursiveSuggestionsObservation(
        liveData: LiveData<SuggestionsUiState>,
        count: Int = 0,
        onContinue: (SuggestionsUiState?) -> Unit
    ) {
        getValue(liveData)?.let { value ->
            if (value.uiState is SuggestionsUiState.StateUi.OnError && count < 10) {
                onContinue(value)
                recursiveSuggestionsObservation(liveData, count + 1, onContinue)
            }
        }
    }

    private fun getListOffersThrow() = flow<List<OfferModel>> {
        throw TestException()
    }

    private fun getListOfCategoriesThrow() = flow<List<MenuCategoryModel>> {
        throw TestException()
    }

    private fun getListOfSuggestionsThrow() = flow<List<SuggestionsModel>> {
        throw TestException()
    }

    private fun getListOffers() = flow {
        emit(listOf(OfferModel(offerId = "offerId")))
    }

    private fun getListOfCategories() = flow {
        emit(listOf(MenuCategoryModel(categoryId = "categoryId")))
    }

    private fun getListOfSuggestions() = flow {
        emit(
            listOf(
                SuggestionsModel(
                    categoryId = "categoryId",
                    suggestionsType = SuggestionsType.ONLY_SUGGESTION,
                    products = null
                )
            )
        )
    }

}
