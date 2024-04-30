package com.dossantos.domain.usecase.suggestions

import com.dossantos.domain.model.suggestions.SuggestionsModel
import com.dossantos.domain.model.suggestions.SuggestionsType
import com.dossantos.domain.repository.suggestions.SuggestionsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyAll
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class SuggestionsUseCaseTest {

    private val suggestionsRepository: SuggestionsRepository = mockk()
    private lateinit var useCase: SuggestionsUseCase

    @Before
    fun setup() {
        useCase = SuggestionsUseCase(suggestionsRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `get suggestions with success`() = runBlocking {
        // Given
        coEvery { suggestionsRepository.getSuggestions() } returns suggestionsFlowResponse()

        // When
        useCase.getSuggestions().single().let { suggestions ->

            // Then
            coVerifyAll { suggestionsRepository.getSuggestions() }
            assert(suggestions.first()?.categoryId == "categoryId")
        }
    }

    @Test
    fun `get suggestions with error`(): Unit = runBlocking {
        // Given
        coEvery { suggestionsRepository.getSuggestions() } throws RuntimeException("crash")

        // When
        try {
            useCase.getSuggestions()
        }catch (exception: Exception) {
            coVerifyAll { suggestionsRepository.getSuggestions() }
            assert(exception.message == "crash")
        }
    }

    @Test
    fun `add suggestions with success`() = runBlocking {
        // Given
        val categoryId = "categoryId"
        every { suggestionsRepository.addSuggestion(categoryId) } returns Unit

        // When
        useCase.addSuggestions(categoryId)

        // Then
        coVerify { suggestionsRepository.addSuggestion(categoryId) }
    }

    @Test
    fun `add suggestions with error`() = runBlocking {
        // Given
        val categoryId = "categoryId"
        coEvery { suggestionsRepository.addSuggestion(categoryId) } throws RuntimeException("crash")

        // When
        try {
            useCase.addSuggestions(categoryId)
        }catch (exception: Exception) {
            // Then
            coVerifyAll { suggestionsRepository.addSuggestion(categoryId) }
            assert(exception.message == "crash")
        }

    }

    private fun suggestionsFlowResponse() = flow<List<SuggestionsModel?>> {
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
