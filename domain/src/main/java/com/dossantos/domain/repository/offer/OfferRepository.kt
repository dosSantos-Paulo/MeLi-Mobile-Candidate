package com.dossantos.domain.repository.offer

import com.dossantos.domain.model.offer.OfferModel
import kotlinx.coroutines.flow.Flow

interface OfferRepository {
    fun getDailyOffers(): Flow<List<OfferModel>>
}
