package com.dossantos.data.repository.offer

import com.dossantos.domain.repository.offer.OfferRepository
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class OfferRepositoryImplTest {

    private lateinit var offerRepository: OfferRepository

    @Before
    fun setup() {
        offerRepository = OfferRepositoryImpl()
    }

    @Test
    fun `get daily offers with success`() = runBlocking {
        // Given -> MockedResult, is always the same response
        val firstOfferId = "MLB1000"

        // When
        offerRepository.getDailyOffers().single().let { offers ->

            // Then
            assert(offers.first().offerId == firstOfferId)
        }
    }
}
