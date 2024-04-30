package com.dossantos.domain.usecase.offer

import com.dossantos.domain.model.offer.OfferModel
import com.dossantos.domain.repository.offer.OfferRepository
import io.mockk.coEvery
import io.mockk.coVerifyAll
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class OfferUseCaseTest {

    private val offerRepository: OfferRepository = mockk()
    private lateinit var useCase: OfferUseCase

    @Before
    fun setup() {
        useCase = OfferUseCase(offerRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `get offers with success`() = runBlocking {
        // Given
        coEvery { offerRepository.getDailyOffers() } returns getFlowOffers()

        // When
        useCase.getOffers().single().let { offers ->

            // Then
            coVerifyAll { offerRepository.getDailyOffers() }
            assert(offers.first().offerId == "offerId")
        }
    }

    @Test
    fun `get offers with error`(): Unit = runBlocking {
        // Given
        coEvery { offerRepository.getDailyOffers() } throws RuntimeException("crash")

        // When
        try {
            useCase.getOffers()
        } catch (exception: Exception) {

            // Then
            coVerifyAll { offerRepository.getDailyOffers() }
            assert(exception.message == "crash")
        }
    }

    private fun getFlowOffers() = flow {
        emit(listOf(OfferModel(offerId = "offerId",)))
    }
}