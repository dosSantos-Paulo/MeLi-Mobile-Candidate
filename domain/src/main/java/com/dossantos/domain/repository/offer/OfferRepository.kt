package com.dossantos.domain.repository.offer

import com.dossantos.domain.model.offer.OfferEntity
import kotlinx.coroutines.flow.Flow

interface OfferRepository {
    fun getDailyOffers(): Flow<List<OfferEntity>>
}
