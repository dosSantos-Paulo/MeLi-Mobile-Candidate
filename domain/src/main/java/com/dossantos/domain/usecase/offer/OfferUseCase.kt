package com.dossantos.domain.usecase.offer

import com.dossantos.domain.repository.offer.OfferRepository

class OfferUseCase(private val offerRepository: OfferRepository) {
    fun getOffers() = offerRepository.getDailyOffers()
}
