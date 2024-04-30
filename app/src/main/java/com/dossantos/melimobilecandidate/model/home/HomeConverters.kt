package com.dossantos.melimobilecandidate.model.home

import com.dossantos.designsystem.model.category.MeLiCategory
import com.dossantos.designsystem.model.offer.MeLiOffer
import com.dossantos.designsystem.model.suggestions.MeLiSuggestion
import com.dossantos.domain.model.category.MenuCategoryModel
import com.dossantos.domain.model.offer.OfferModel
import com.dossantos.domain.model.search.ProductsModel
import com.dossantos.domain.model.suggestions.SuggestionsModel
import com.dossantos.domain.model.suggestions.SuggestionsType

fun OfferModel.toMeLiOffer() = MeLiOffer(
    imageUrl = this.offerImageUrl,
    id = this.offerId,
    contentDescription = this.offerContentDescription
)

fun MenuCategoryModel.toMeLiCategory() = MeLiCategory(
    name = this.categoryName,
    imageUrl = this.categoryImage,
    id = this.categoryId
)

fun List<SuggestionsModel?>.toMeLiSuggestion(): List<Pair<SuggestionsType, List<MeLiSuggestion>?>> {
    val list = mutableListOf<Pair<SuggestionsType, List<MeLiSuggestion>?>>()
    forEach { suggestions ->
        suggestions?.run {
            list.add(suggestionsType to products?.map { product -> product.toMeLiSuggestion() })
        }
    }
    return list.toList()
}

fun ProductsModel.toMeLiSuggestion() = MeLiSuggestion(
    itemId = id,
    title = title,
    lastPrice = originalPrice,
    discountPercentage = itemDiscount,
    price = price,
    imageUrl = thumbnail
)
