package com.dossantos.data.repository.product

import com.dossantos.data.model.product.ProductResponseDetailDto
import com.dossantos.data.model.product.ProductResponseDto
import com.dossantos.data.network.product.MeLiProductEndpoint
import com.dossantos.domain.repository.product.ProductRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class ProductRepositoryImplTest {
    private val productEndpoint: MeLiProductEndpoint = mockk()
    private lateinit var repository: ProductRepository

    @Before
    fun setup() {
        repository = ProductRepositoryImpl(productEndpoint)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `get product info by id with success`() = runBlocking {
        // Given
        val productId = "productId"
        coEvery { productEndpoint.getProductById(productId) } returns getResponseProduct()
        coEvery { productEndpoint.getProductDetailById(productId) } returns getProductDetail()

        // When
        repository.getProductById(productId).single().let { response ->

            // Then
            coVerifySequence {
                productEndpoint.getProductById(productId)
                productEndpoint.getProductDetailById(productId)
            }
            assert(response.categoryId == "categoryId")
        }
    }

    @Test
    fun `get product info by id on error`(): Unit = runBlocking {
        // Given
        val productId = "productId"
        coEvery { productEndpoint.getProductById(productId) } throws RuntimeException("")

        // When
        try {
            repository.getProductById(productId)
        } catch (ex: Exception) {
            // Then
            coVerify { productEndpoint.getProductById(productId) }
            assert(ex.message == "crash")
        }
    }

    private fun getResponseProduct() = ProductResponseDto(
        id = "productId",
        title = "title",
        categoryId = "categoryId",
        price = 4.5,
        originalPrice = 10.0,
        currencyId = "BRL",
        permalink = "",
        pictures = null,
        warranty = null,
    )

    private fun getProductDetail() = ProductResponseDetailDto(productDescription = "description")
}