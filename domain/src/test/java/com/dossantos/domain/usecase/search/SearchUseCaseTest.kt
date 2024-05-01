package com.dossantos.domain.usecase.search

import com.dossantos.domain.model.search.ProductsModel
import com.dossantos.domain.model.search.SearchResponseModel
import com.dossantos.domain.repository.search.SearchRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class SearchUseCaseTest {

    private val searchRepository: SearchRepository = mockk()
    private lateinit var useCase: SearchUseCase

    @Before
    fun setup() {
        useCase = SearchUseCase(searchRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `search by string with success`() = runBlocking {
        // Given
        val search = "search"
        val offset = 10
        coEvery { searchRepository.searchByString(search, offset) } returns getSearchResponse()

        // When
        useCase.search(search, offset).single().let { response ->

            // Then
            coVerify { searchRepository.searchByString(search, offset) }
            assert(response.products?.first()?.id == "productId")
        }
    }

    @Test
    fun `search by string with error`(): Unit = runBlocking {
        // Given
        val search = "search"
        val offset = 10
        coEvery { searchRepository.searchByString(search, offset) } throws RuntimeException("crash")

        // When
        try {
            useCase.search(search, offset)
        }catch (exception: Exception) {

            // Then
            coVerify { searchRepository.searchByString(search, offset) }
            assert(exception.message == "crash")
        }
    }

    @Test
    fun `search by category with success`() = runBlocking {
        // Given
        val search = "ABC0001"
        val offset = 10
        coEvery { searchRepository.searchByCategory(search) } returns getSearchResponse()

        // When
        useCase.search(search, offset).single().let { response ->

            // Then
            coVerify { searchRepository.searchByCategory(search) }
            assert(response.products?.first()?.id == "productId")
        }
    }

    @Test
    fun `search by category with error`(): Unit = runBlocking {
        // Given
        val search = "ABC0001"
        val offset = 10
        coEvery { searchRepository.searchByCategory(search) } throws RuntimeException("crash")

        // When
        try {
            useCase.search(search, offset)
        }catch (exception: Exception) {
            // Then
            coVerify { searchRepository.searchByCategory(search) }
            assert(exception.message == "crash")
        }
    }

    private fun getSearchResponse() = flow {
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
