package com.dossantos.melimobilecandidate.viewmodel.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dossantos.designsystem.model.suggestions.MeLiProducts
import com.dossantos.domain.model.search.ProductsModel
import com.dossantos.domain.model.search.SearchResponseModel
import com.dossantos.domain.usecase.search.SearchUseCase
import com.dossantos.melimobilecandidate.TestException
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import io.mockk.slot
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val searchUseCase: SearchUseCase = mockk()
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.IO)
        viewModel = SearchViewModel(searchUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `init search with success`() = runCatching {
        // Given
        val searchString = "search"
        val observer = mockk<Observer<in SearchStateUi>>()
        val slot = slot<SearchStateUi>()
        coEvery { observer.onChanged(capture(slot)) } answers {}
        coEvery { searchUseCase.search(searchString, 10) } returns getSearchResponse()

        // When
        viewModel.initSearch(searchString)
        viewModel.searchStateUi.observeForever(observer)

        // Then
        coVerifySequence {
            observer.onChanged(SearchStateUi().onLoading())
            observer.onChanged(
                SearchStateUi().onSuccess(
                    listOf(
                        MeLiProducts(
                            itemId = "productId",
                            title = null,
                            lastPrice = null,
                            discountPercentage = null,
                            price = null,
                            imageUrl = null
                        )
                    )
                )
            )
        }

        viewModel.searchStateUi.removeObserver(observer)
    }

    @Test
    fun `init search with fail`() = runCatching {
        // Given
        val searchString = "search"
        val observer = mockk<Observer<in SearchStateUi>>()
        val slot = slot<SearchStateUi>()
        coEvery { observer.onChanged(capture(slot)) } answers {}
        coEvery { searchUseCase.search(searchString, 0) } returns flow { TestException() }

        // When
        viewModel.initSearch(searchString)
        viewModel.searchStateUi.observeForever(observer)

        // Then
        coVerifySequence {
            observer.onChanged(SearchStateUi().onLoading())
            observer.onChanged(SearchStateUi().onError())
        }

        viewModel.searchStateUi.removeObserver(observer)
    }

    fun getSearchResponse() = flow {
        emit(
            SearchResponseModel(
                pagingInfo = null,
                products = listOf(
                    ProductsModel(
                        id = "productId",
                        title = null,
                        price = null,
                        originalPrice = null,
                        categoryId = null,
                        thumbnail = null,
                        installments = null,
                        itemDiscount = null
                    )
                )
            )
        )
    }
}
