package com.dossantos.domain.offer

import kotlinx.coroutines.flow.Flow

interface OfferRepository {
    fun getDailyOffers(): Flow<List<OfferEntity>>
}