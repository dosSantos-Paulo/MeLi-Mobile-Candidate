package com.dossantos.data.repository.search

import com.dossantos.data.model.search.SearchInstallmentsDto
import com.dossantos.data.model.search.SearchProductDto
import com.dossantos.data.model.search.SearchResponseDto
import com.dossantos.data.network.search.MeLiSearchEndpoint
import com.dossantos.data.repository.search.SearchRepositoryImpl.Companion.toModel
import com.dossantos.domain.repository.search.SearchRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class SearchRepositoryImplTest {

    private val searchEndpoint: MeLiSearchEndpoint = mockk()
    private lateinit var searchRepository: SearchRepository

    @Before
    fun setup() {
        searchRepository = SearchRepositoryImpl(searchEndpoint)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test search by category with success`() = runBlocking {
        // Given
        val categoryId = "categoryId"
        coEvery { searchEndpoint.searchByCategory(categoryId = categoryId) } returns searchResponseDto()

        // When
        searchRepository.searchByCategory(categoryId).single().let {


            // Then
            coVerify { searchEndpoint.searchByCategory(categoryId = categoryId) }
        }
    }

    @Test
    fun `test search by category with fail`(): Unit = runBlocking {
        // Given
        val categoryId = "categoryId"
        coEvery { searchEndpoint.searchByCategory(categoryId = categoryId) } throws RuntimeException(
            "crash"
        )

        // When
        searchRepository.searchByCategory(categoryId).catch {

            // Then
            assert("crash" == it.message)
        }
    }

    @Test
    fun `test search by categories with success`() = runBlocking {
        // Given
        val categoriesId =
            listOf("categoryId", "categoryId", "categoryId", "categoryId")
        coEvery { searchEndpoint.searchByCategory(categoryId = "categoryId") } returns searchResponseDto()

        // When
        searchRepository.searchByCategories(categoriesId).single().let {


            // Then
            coVerify(exactly = 4) { searchEndpoint.searchByCategory(categoryId = "categoryId") }
        }
    }

    @Test
    fun `test search by categories with fail`(): Unit = runBlocking {
        // Given
        val categoriesId =
            listOf("categoryId", "categoryId", "categoryId", "categoryId")
        coEvery { searchEndpoint.searchByCategory(categoryId = "categoryId") } throws RuntimeException(
            "crash"
        )

        // When
        searchRepository.searchByCategories(categoriesId).catch {

            // Then
            assert("crash" == it.message)
        }
    }

    @Test
    fun `test search by string with success`() = runBlocking {
        // Given
        val string = "currentSearch"
        coEvery { searchEndpoint.searchByString(string = "currentSearch") } returns searchResponseDto()

        // When
        searchRepository.searchByString(string,).single().let {


            // Then
            coVerify { searchEndpoint.searchByString(string = string) }
        }
    }

    @Test
    fun `test search by string with fail`(): Unit = runBlocking {
        // Given
        coEvery { searchEndpoint.searchByString(string = "categoryId") } throws RuntimeException(
            "crash"
        )

        // When
        searchRepository.searchByString(string = "categoryId",).catch {

            // Then
            assert("crash" == it.message)
        }
    }

    @Test
    fun `search model converter`(): Unit = runBlocking {
        // Given
        val model = searchResponseDto()

        // When
        val newModel = model.toModel()

        // Then
        model.results?.first()?.price?.toString()?.contains("R$")?.let { assert(it.not()) }
        newModel.products?.first()?.price?.contains("R$")?.let { assert(it) }
    }

    private fun searchResponseDto() = SearchResponseDto(
        paging = null,
        results = listOf(
            SearchProductDto(
                id = "1",
                title = "capinha de celular",
                price = 100.0,
                originalPrice = 120.0,
                categoryId = "categoryId",
                thumbnail = "thumb",
                installments = SearchInstallmentsDto(currencyId = "BRL")
            ),
        )
    )
}
