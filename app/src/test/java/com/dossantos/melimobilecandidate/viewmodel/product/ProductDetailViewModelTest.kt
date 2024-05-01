package com.dossantos.melimobilecandidate.viewmodel.product

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dossantos.designsystem.model.product.MeLiProductModel
import com.dossantos.domain.model.product.ProductResponseModel
import com.dossantos.domain.usecase.products.ProductsUseCase
import com.dossantos.melimobilecandidate.TestException
import com.dossantos.melimobilecandidate.viewmodel.home.OfferUiState
import io.mockk.coEvery
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
class ProductDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val productId = "productId"
    private val productsUseCase: ProductsUseCase = mockk()
    private lateinit var productDetailViewModel: ProductDetailViewModel


    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.IO)
        productDetailViewModel = ProductDetailViewModel(productId, productsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `get product by id with success`() = runBlocking {
        // Given
        val observer = mockk<Observer<in ProductDetailStateUi>>()
        val slot = slot<ProductDetailStateUi>()
        coEvery { observer.onChanged(capture(slot)) } answers {}
        coEvery { productsUseCase.getProductById(productId) } returns getProductResponseModel()

        // When
        productDetailViewModel.init()
        productDetailViewModel.productDetailStateUi.observeForever(observer)

        // Then
        coVerifySequence {
            observer.onChanged(ProductDetailStateUi().onLoading())
            observer.onChanged(
                ProductDetailStateUi().onSuccess(
                    MeLiProductModel(
                        id = productId,
                        title = "title",
                        categoryId = "categoryId",
                        price = null,
                        originalPrice = null,
                        discountAmount = null,
                        currencyId = null,
                        permalink = null,
                        picturesUrl = null,
                        warranty = null,
                        productDescription = null
                    )
                )
            )
        }

        productDetailViewModel.productDetailStateUi.observeForever(observer)
    }

    @Test
    fun `get product by id with error`() = runBlocking {
        // Given
        val observer = mockk<Observer<in ProductDetailStateUi>>()
        val slot = slot<ProductDetailStateUi>()
        coEvery { observer.onChanged(capture(slot)) } answers {}
        coEvery { productsUseCase.getProductById(productId) } returns flow { throw TestException() }

        // When
        productDetailViewModel.init()
        productDetailViewModel.productDetailStateUi.observeForever(observer)

        // Then
        coVerifySequence {
            observer.onChanged(ProductDetailStateUi().onLoading())
            observer.onChanged(ProductDetailStateUi().onError())
        }

        productDetailViewModel.productDetailStateUi.observeForever(observer)
    }

    private fun getProductResponseModel() = flow {
        emit(
            ProductResponseModel(
                id = productId,
                title = "title",
                categoryId = "categoryId",
                price = null,
                originalPrice = null,
                discountAmount = null,
                currencyId = null,
                permalink = null,
                picturesUrl = null,
                warranty = null,
                productDescription = null
            )
        )
    }
}