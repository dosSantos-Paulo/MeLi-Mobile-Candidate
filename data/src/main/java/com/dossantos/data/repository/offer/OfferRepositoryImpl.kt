package com.dossantos.data.repository.offer

import com.dossantos.data.model.offer.OfferDao
import com.dossantos.data.model.offer.OfferModel
import com.dossantos.domain.model.offer.OfferEntity
import com.dossantos.domain.repository.offer.OfferRepository
import kotlinx.coroutines.flow.flow

class OfferRepositoryImpl : OfferRepository {

    override fun getDailyOffers() = flow {
        emit(
            OfferDao(getOffers())
                .offers.map { offers -> offers.toEntity() }
        )
    }

    private fun getOffers() = listOf(
        OfferModel(
            offerId = "MLB1000",
            offerImageUrl = "https://http2.mlstatic.com/D_NQ_667836-MLA74349586053_022024-OO.webp",
            offerContentDescription = "Eletrônicos, Áudio e Vídeo"
        ),
        OfferModel(
            offerId = "MLB5726",
            offerImageUrl = "https://http2.mlstatic.com/D_NQ_827152-MLA76036618585_042024-OO.webp",
            offerContentDescription = "Eletrodomésticos"
        ),
        OfferModel(
            offerId = "MLB1574",
            offerImageUrl = "https://http2.mlstatic.com/D_NQ_745423-MLA75956703413_042024-OO.webp",
            offerContentDescription = "Casa, Móveis e Decoração"
        ),
        OfferModel(
            offerId = "MLB1276",
            offerImageUrl = "https://http2.mlstatic.com/D_NQ_841049-MLA75787900326_042024-OO.webp",
            offerContentDescription = "Esportes e Fitness"
        ),
        OfferModel(
            offerId = "MLB1051",
            offerImageUrl = "https://http2.mlstatic.com/D_NQ_841049-MLA75787900326_042024-OO.webp",
            offerContentDescription = "Celulares e Telefones"
        ),
        OfferModel(
            offerId = "MLB1276",
            offerImageUrl = "https://http2.mlstatic.com/D_NQ_761643-MLA76004601443_042024-OO.webp",
            offerContentDescription = "Esportes e Fitness"
        )
    )

    companion object {
        fun OfferModel.toEntity() = OfferEntity(
            offerId = this.offerId,
            offerImageUrl = this.offerImageUrl,
            offerContentDescription = this.offerContentDescription
        )
    }
}
