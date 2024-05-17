package com.dossantos.melimobilecandidate.viewmodel.search

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dossantos.designsystem.model.suggestions.MeLiProducts
import com.dossantos.domain.model.search.ProductsModel
import com.dossantos.domain.model.search.SearchResponseModel
import com.dossantos.domain.usecase.search.SearchUseCase
import com.dossantos.melimobilecandidate.TestException
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
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

    private val useCase: SearchUseCase = mockk()
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.IO)
        viewModel = SearchViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `initSearch with success`() = runBlocking {
        // Given
        val searchString = "search"
        val paging = 0
        val observer = mockk<Observer<in SearchStateUi>>()
        val slot = slot<SearchStateUi>()
        coEvery { observer.onChanged(capture(slot)) } just Runs
        coEvery {
            useCase.searchProductByString(searchString, paging)
        } returns getSearch()

        // When
        viewModel.initSearch(searchString)
        viewModel.searchStateUi.observeForever(observer)

        // Then
        delay(1000) // this delay is needed to help runBlock to await all responses
        coVerifySequence {
            observer.onChanged(SearchStateUi(SearchStateUi.StateUi.ShowLoading))
            observer.onChanged(
                SearchStateUi(
                    SearchStateUi.StateUi.OnSuccess(
                        products = listOf(
                            MeLiProducts(
                                itemId = "productId",
                                title = null,
                                lastPrice = null,
                                discountPercentage = null,
                                price = null,
                                imageUrl = null,
                            )
                        )
                    )
                )
            )
        }
    }

    @Test
    fun `initSearch with error`() = runBlocking {
        // Given
        val searchString = "search"
        val paging = 0
        val observer = mockk<Observer<in SearchStateUi>>()
        val slot = slot<SearchStateUi>()
        coEvery { observer.onChanged(capture(slot)) } just Runs
        coEvery {
            useCase.searchProductByString(searchString, paging)
        } returns getSearchWithException()

        // When
        viewModel.initSearch(searchString)
        viewModel.searchStateUi.observeForever(observer)

        // Then
        delay(1000) // this delay is needed to help runBlock to await all responses
        coVerifySequence {
            observer.onChanged(SearchStateUi(SearchStateUi.StateUi.ShowLoading))
            observer.onChanged(SearchStateUi(SearchStateUi.StateUi.OnError))
        }
    }

    @Test
    fun `tryNextPage with success`() = runBlocking {
        // Given
        val searchString = "search"
        val page = 0
        val nextPage = 1
        val observer = mockk<Observer<in SearchStateUi>>()
        val slot = slot<SearchStateUi>()
        coEvery { observer.onChanged(capture(slot)) } just Runs
        coEvery {
            useCase.searchProductByString(searchString, page)
        } returns getSearch()
        coEvery {
            useCase.searchProductByString(searchString, nextPage)
        } returns getSearchNextPage()

        // When
        viewModel.initSearch(searchString)
        viewModel.searchStateUi.observeForever(observer)

        delay(1000) // simulate user interaction
        viewModel.tryNextPage()

        // Then
        delay(5000) // this delay is needed to help runBlock to await all responses
        coVerifySequence {
            observer.onChanged(SearchStateUi(SearchStateUi.StateUi.ShowLoading))
            observer.onChanged(
                SearchStateUi(
                    SearchStateUi.StateUi.OnSuccess(
                        products = listOf(
                            MeLiProducts(
                                itemId = "productId",
                                title = null,
                                lastPrice = null,
                                discountPercentage = null,
                                price = null,
                                imageUrl = null,
                            )
                        )
                    )
                )
            )
            observer.onChanged(SearchStateUi(SearchStateUi.StateUi.ShowLoading))
            observer.onChanged(
                SearchStateUi(
                    SearchStateUi.StateUi.OnSuccess(
                        products = listOf(
                            MeLiProducts(
                                itemId = "productId-NextPage",
                                title = null,
                                lastPrice = null,
                                discountPercentage = null,
                                price = null,
                                imageUrl = null,
                            )
                        )
                    )
                )
            )
        }
    }

    @Test
    fun `retry with success`() = runBlocking {
        // Given
        val searchString = "search"
        val page = 0
        var useCaseResponseCount = 0
        val observer = mockk<Observer<in SearchStateUi>>()
        val slot = slot<SearchStateUi>()
        coEvery { observer.onChanged(capture(slot)) } just Runs
        coEvery {
            useCase.searchProductByString(searchString, page)
        } returns getSearchWithException()
        coEvery {
            useCase.searchProductByString(searchString, page)
        } coAnswers {
            if (useCaseResponseCount == 0) {
                useCaseResponseCount++
                getSearchWithException()
            } else getSearch()
        }

        // When
        viewModel.initSearch(searchString)
        viewModel.searchStateUi.observeForever(observer)

        delay(1000) // simulate user interaction
        viewModel.retrySearch()

        // Then
        delay(5000) // this delay is needed to help runBlock to await all responses
        coVerifySequence {
            observer.onChanged(SearchStateUi(SearchStateUi.StateUi.ShowLoading))
            observer.onChanged(SearchStateUi(SearchStateUi.StateUi.OnError))
            observer.onChanged(SearchStateUi(SearchStateUi.StateUi.ShowLoading))
            observer.onChanged(
                SearchStateUi(
                    SearchStateUi.StateUi.OnSuccess(
                        products = listOf(
                            MeLiProducts(
                                itemId = "productId",
                                title = null,
                                lastPrice = null,
                                discountPercentage = null,
                                price = null,
                                imageUrl = null,
                            )
                        )
                    )
                )
            )
        }
    }

    @Test
    fun `retry with error`() = runBlocking {
        // Given
        val searchString = "search"
        val page = 0
        val observer = mockk<Observer<in SearchStateUi>>()
        val slot = slot<SearchStateUi>()
        coEvery { observer.onChanged(capture(slot)) } just Runs
        coEvery {
            useCase.searchProductByString(searchString, page)
        } returns getSearchWithException()

        // When
        viewModel.initSearch(searchString)
        viewModel.searchStateUi.observeForever(observer)

        for (i in 0 until 10) {
            Log.d("Retry", "Attempt $i")
            delay(100) // simulate simultaneous retry attempts
            viewModel.retrySearch()
        }

        // Then
        delay(5000) // this delay is needed to help runBlock to await all responses
        coVerify(exactly = 5) {
            observer.onChanged(SearchStateUi(SearchStateUi.StateUi.ShowLoading))
            observer.onChanged(SearchStateUi(SearchStateUi.StateUi.OnError))
        }
    }

    private fun getSearchWithException() = flow<SearchResponseModel> {
        throw TestException()
    }

    private fun getSearch() = flow {
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

    private fun getSearchNextPage() = flow {
        emit(
            SearchResponseModel(
                pagingInfo = null,
                products = listOf(
                    ProductsModel(
                        id = "productId-NextPage",
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
