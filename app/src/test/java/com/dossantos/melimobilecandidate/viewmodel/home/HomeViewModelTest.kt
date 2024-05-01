package com.dossantos.melimobilecandidate.viewmodel.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dossantos.designsystem.model.category.MeLiCategory
import com.dossantos.designsystem.model.offer.MeLiOffer
import com.dossantos.domain.model.category.MenuCategoryModel
import com.dossantos.domain.model.offer.OfferModel
import com.dossantos.domain.model.suggestions.SuggestionsModel
import com.dossantos.domain.model.suggestions.SuggestionsType
import com.dossantos.domain.usecase.category.CategoryMenuUseCase
import com.dossantos.domain.usecase.offer.OfferUseCase
import com.dossantos.domain.usecase.suggestions.SuggestionsUseCase
import com.dossantos.melimobilecandidate.TestException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.mockk
import io.mockk.slot
import io.mockk.unmockkAll
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
        val observer = mockk<Observer<in OfferUiState>>()
        val slot = slot<OfferUiState>()
        coEvery { observer.onChanged(capture(slot)) } answers {}
        coEvery { offerUseCase.getOffers() } returns getListOffers()
        coEvery { categoryMenuUseCase.getMenuCategory() } returns getListOfCategories()
        coEvery { suggestionsUseCase.getSuggestions() } returns getListOfSuggestions()

        // When
        viewModel.init()
        viewModel.offerUiState.observeForever(observer)

        // Then
        coVerifySequence {
            observer.onChanged(OfferUiState().onLoading())
            observer.onChanged(
                OfferUiState().onSuccess(
                    listOf(
                        MeLiOffer(
                            imageUrl = null,
                            id = "offerId",
                            contentDescription = null
                        )
                    )
                )
            )
        }

        viewModel.offerUiState.removeObserver(observer)
    }

    @Test
    fun `test getOffers with error`() = runBlocking {
        // Given
        val observer = mockk<Observer<in OfferUiState>>()
        val slot = slot<OfferUiState>()
        coEvery { observer.onChanged(capture(slot)) } answers {}
        coEvery { offerUseCase.getOffers() } returns getListOffersThrow()
        coEvery { categoryMenuUseCase.getMenuCategory() } returns getListOfCategories()
        coEvery { suggestionsUseCase.getSuggestions() } returns getListOfSuggestions()

        // When
        viewModel.init()
        viewModel.offerUiState.observeForever(observer)

        // Then
        coVerifySequence {
            observer.onChanged(OfferUiState().onLoading())
            observer.onChanged(OfferUiState().onError())
        }

        viewModel.offerUiState.removeObserver(observer)
    }

    @Test
    fun `test getMenuCategory with success`() = runBlocking {
        // Given
        val observer = mockk<Observer<in CategoryMenuUiState>>()
        val slot = slot<CategoryMenuUiState>()
        coEvery { observer.onChanged(capture(slot)) } answers {}
        coEvery { offerUseCase.getOffers() } returns getListOffers()
        coEvery { categoryMenuUseCase.getMenuCategory() } returns getListOfCategories()
        coEvery { suggestionsUseCase.getSuggestions() } returns getListOfSuggestions()

        // When
        viewModel.init()
        viewModel.categoryMenuUiState.observeForever(observer)

        // Then
        coVerifySequence {
            observer.onChanged(CategoryMenuUiState().onLoading())
            observer.onChanged(
                CategoryMenuUiState().onSuccess(
                    listOf(
                        MeLiCategory(
                            name = null,
                            imageUrl = null,
                            id = "categoryId"
                        )
                    )
                )
            )
        }

        viewModel.categoryMenuUiState.removeObserver(observer)
    }

    @Test
    fun `test getMenuCategory with error`() = runBlocking {
        // Given
        val observer = mockk<Observer<in CategoryMenuUiState>>()
        val slot = slot<CategoryMenuUiState>()
        coEvery { observer.onChanged(capture(slot)) } answers {}
        coEvery { offerUseCase.getOffers() } returns getListOffers()
        coEvery { categoryMenuUseCase.getMenuCategory() } returns getListOfCategoriesThrow()
        coEvery { suggestionsUseCase.getSuggestions() } returns getListOfSuggestions()

        // When
        viewModel.init()
        viewModel.categoryMenuUiState.observeForever(observer)

        // Then
        coVerifySequence {
            observer.onChanged(CategoryMenuUiState().onLoading())
            observer.onChanged(CategoryMenuUiState().onError())
        }

        viewModel.categoryMenuUiState.removeObserver(observer)
    }

    @Test
    fun `test getSuggestions with success`() = runBlocking {
        // Given
        val observer = mockk<Observer<in SuggestionsUiState>>()
        val slot = slot<SuggestionsUiState>()
        coEvery { observer.onChanged(capture(slot)) } answers {}
        coEvery { offerUseCase.getOffers() } returns getListOffers()
        coEvery { categoryMenuUseCase.getMenuCategory() } returns getListOfCategories()
        coEvery { suggestionsUseCase.getSuggestions() } returns getListOfSuggestions()

        // When
        viewModel.init()
        viewModel.suggestionsUiState.observeForever(observer)

        // Then
        coVerifySequence {
            observer.onChanged(SuggestionsUiState().onLoading())
            observer.onChanged(SuggestionsUiState().onSuccess(listOf(SuggestionsType.ONLY_SUGGESTION to null)))
        }

        viewModel.suggestionsUiState.removeObserver(observer)
    }

    @Test
    fun `test getSuggestions with error`() = runBlocking {
        // Given
        val observer = mockk<Observer<in SuggestionsUiState>>()
        val slot = slot<SuggestionsUiState>()
        coEvery { observer.onChanged(capture(slot)) } answers {}
        coEvery { offerUseCase.getOffers() } returns getListOffers()
        coEvery { categoryMenuUseCase.getMenuCategory() } returns getListOfCategories()
        coEvery { suggestionsUseCase.getSuggestions() } returns getListOfSuggestionsThrow()

        // When
        viewModel.init()
        viewModel.suggestionsUiState.observeForever(observer)

        // Then
        coVerifySequence {
            observer.onChanged(SuggestionsUiState().onLoading())
            observer.onChanged(SuggestionsUiState().onError())
        }

        viewModel.suggestionsUiState.removeObserver(observer)
    }

    @Test
    fun `test getOffers retry with success`() = runBlocking {
        // Given
        val observer = mockk<Observer<in OfferUiState>>()
        val slot = slot<OfferUiState>()
        coEvery { observer.onChanged(capture(slot)) } answers {}
        coEvery { offerUseCase.getOffers() } returns getListOffers()

        // When
        viewModel.retryOffer()
        viewModel.offerUiState.observeForever(observer)

        // Then
        coVerifySequence {
            observer.onChanged(OfferUiState().onLoading())
            observer.onChanged(
                OfferUiState().onSuccess(
                    listOf(
                        MeLiOffer(
                            imageUrl = null,
                            id = "offerId",
                            contentDescription = null
                        )
                    )
                )
            )
        }

        viewModel.offerUiState.removeObserver(observer)
    }

    @Test
    fun `test getOffers retry with error`() = runBlocking {
        // Given
        val observer = mockk<Observer<in OfferUiState>>()
        val slot = slot<OfferUiState>()
        coEvery { observer.onChanged(capture(slot)) } answers { viewModel.retryOffer() }
        coEvery { offerUseCase.getOffers() } returns getListOffersThrow()
        coEvery { categoryMenuUseCase.getMenuCategory() } returns getListOfCategories()
        coEvery { suggestionsUseCase.getSuggestions() } returns getListOfSuggestions()

        // When
        viewModel.init()
        viewModel.offerUiState.observeForever(observer)

        // Then
        coVerify(exactly = 4) { offerUseCase.getOffers() }

        viewModel.offerUiState.removeObserver(observer)
    }

    @Test
    fun `test category retry with error`() = runBlocking {
        // Given
        val observer = mockk<Observer<in CategoryMenuUiState>>()
        val slot = slot<CategoryMenuUiState>()
        coEvery { observer.onChanged(capture(slot)) } answers {
            viewModel.retryCategoryMenu()
        }
        coEvery { offerUseCase.getOffers() } returns getListOffers()
        coEvery { categoryMenuUseCase.getMenuCategory() } returns getListOfCategoriesThrow()
        coEvery { suggestionsUseCase.getSuggestions() } returns getListOfSuggestions()

        // When
        viewModel.init()
        viewModel.categoryMenuUiState.observeForever(observer)

        // Then
        coVerify(exactly = 4) { categoryMenuUseCase.getMenuCategory() }

        viewModel.categoryMenuUiState.removeObserver(observer)
    }

    @Test
    fun `test suggestions retry with error`() = runBlocking {
        // Given
        val observer = mockk<Observer<in SuggestionsUiState>>()
        val slot = slot<SuggestionsUiState>()
        coEvery { observer.onChanged(capture(slot)) } answers {
            viewModel.retrySuggestions()
        }
        coEvery { offerUseCase.getOffers() } returns getListOffers()
        coEvery { categoryMenuUseCase.getMenuCategory() } returns getListOfCategories()
        coEvery { suggestionsUseCase.getSuggestions() } returns getListOfSuggestionsThrow()

        // When
        viewModel.init()
        viewModel.suggestionsUiState.observeForever(observer)

        // Then
        coVerify(exactly = 4) { suggestionsUseCase.getSuggestions() }

        viewModel.suggestionsUiState.removeObserver(observer)
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
