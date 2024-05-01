package com.dossantos.data.repository.suggestions

import com.dossantos.data.database.MeLiSuggestionsDao
import com.dossantos.data.model.search.SearchInstallmentsDto
import com.dossantos.data.model.search.SearchProductDto
import com.dossantos.data.model.search.SearchResponseDto
import com.dossantos.data.model.suggestions.SuggestionsEntity
import com.dossantos.data.network.search.MeLiSearchEndpoint
import com.dossantos.domain.repository.suggestions.SuggestionsRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.spyk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class SuggestionsRepositoryImplTest {

    private val suggestionsDao: MeLiSuggestionsDao = spyk()
    private val searchEndpoint: MeLiSearchEndpoint = mockk()
    private lateinit var suggestionsRepository: SuggestionsRepository

    @Before
    fun setup() {
        suggestionsRepository = SuggestionsRepositoryImpl(suggestionsDao, searchEndpoint)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test add new object on table`() {
        // Given
        val categoryId = "categoryId"
        val entitySlot = slot<SuggestionsEntity>()

        // When
        suggestionsRepository.addSuggestion(categoryId)
        verify { suggestionsDao.insertSuggestion(capture(entitySlot)) }

        // Then
        assert(categoryId == entitySlot.captured.categoryId)
    }

    @Test
    fun `test add new object on table with fail`() {
        // Given
        val categoryId = "categoryId"
        every { suggestionsDao.insertSuggestion(any<SuggestionsEntity>()) } throws RuntimeException("Crash")

        // When
        try {
            suggestionsRepository.addSuggestion(categoryId)
        }catch (ex: Exception) {

            // Then
            assert(ex.message == "Crash")
        }
    }

    @Test
    fun `test get sugestionsMock with empty table`() = runBlocking {
        // Given
        every { suggestionsDao.getSuggestions() } returns listOf()
        coEverySearch("MLB1000")
        coEverySearch("MLB1574")
        coEverySearch("MLB1276")
        coEverySearch("MLB1051")

        // When
        suggestionsRepository.getSuggestions().singleOrNull().let {

            // Then
            assert(searchResponseDto().results?.first()?.title == it?.first()?.products?.first()?.title)
        }
    }

    @Test
    fun `test get sugestionsMock with empty table fail`(): Unit = runBlocking {
        // Given
        every { suggestionsDao.getSuggestions() } throws RuntimeException("Crash")

        // When
        suggestionsRepository.getSuggestions().catch {

            // Then
            assert(it.message == "Crash")

        }
    }

    @Test
    fun `test get sugestionsMock with not empty table`() = runBlocking {
        // Given
        every { suggestionsDao.getSuggestions() } returns suggestions()

        coEverySearch("MBL0001")

        // When
        suggestionsRepository.getSuggestions().singleOrNull().let {

            // Then
            assert("categoryId" == it?.first()?.products?.first()?.categoryId)
        }
    }

    private fun coEverySearch(categoryId: String) = coEvery {
        searchEndpoint.searchByCategory(
            categoryId = categoryId,
            limit = 4
        )
    } returns searchResponseDto()

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

    private fun suggestions(): List<SuggestionsEntity> = listOf(
        SuggestionsEntity(categoryId = "MBL0001", dateTimeMills = 1L, suggestionType = "suggestionType")
    )
}
