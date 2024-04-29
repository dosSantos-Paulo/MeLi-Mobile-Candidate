package com.dossantos.domain.offer

class OfferUseCase(private val offerRepository: OfferRepository) {
    fun getOffers() = offerRepository.getDailyOffers()
}