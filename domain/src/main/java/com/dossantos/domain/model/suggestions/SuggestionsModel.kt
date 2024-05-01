package com.dossantos.domain.model.suggestions

import com.dossantos.domain.model.search.ProductsModel

data class SuggestionsModel(
    val categoryId: String,
    val suggestionsType: SuggestionsType,
    val products: List<ProductsModel>?
)
