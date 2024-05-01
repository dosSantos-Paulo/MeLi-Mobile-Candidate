package com.dossantos.domain.usecase.products

import com.dossantos.domain.model.product.ProductResponseModel
import com.dossantos.domain.repository.product.ProductRepository
import com.dossantos.domain.repository.suggestions.SuggestionsRepository
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class ProductsUseCaseTest {
    private val productRepository: ProductRepository = mockk()
    private val suggestionsRepository: SuggestionsRepository = mockk()
    private lateinit var useCase: ProductsUseCase

    @Before
    fun setup() {
        useCase = ProductsUseCase(productRepository, suggestionsRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test get product info and save new suggestion`() = runBlocking {
        // Given
        val productId = "productId"
        val categoryId = "categoryId"
        coEvery { productRepository.getProductById(productId) } returns getProductResponseModel()
        coEvery { suggestionsRepository.addSuggestion(categoryId) } answers {}

        // When
        useCase.getProductById(productId).single().let { product ->

            // Then
            coVerifySequence {
                productRepository.getProductById(productId)
                suggestionsRepository.addSuggestion(categoryId)
            }

            assert(productId == product.id)
        }
    }

    @Test
    fun `test get product with error`(): Unit = runBlocking {
        // Given
        val productId = "productId"
        coEvery { productRepository.getProductById(productId) } returns getProductsResponseThrow()

        // When
        try {
            useCase.getProductById(productId)
        }catch (ex: Exception) {
            // Then
            assert(ex.message == "crash")
        }
    }

    private fun getProductResponseModel() = flow {
        emit(
            ProductResponseModel(
                id = "productId",
                title = "productTitle",
                categoryId = "categoryId",
                price = "1,0",
                originalPrice = null,
                discountAmount = null,
                currencyId = "BRL",
                permalink = null,
                picturesUrl = null,
                warranty = null,
                productDescription = null
            )
        )
    }

    private fun getProductsResponseThrow() =
        flow<ProductResponseModel> { throw NullPointerException("crash") }

}
