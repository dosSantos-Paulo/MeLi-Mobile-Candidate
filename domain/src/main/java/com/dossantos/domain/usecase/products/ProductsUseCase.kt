package com.dossantos.domain.usecase.products

import com.dossantos.domain.repository.product.ProductRepository
import com.dossantos.domain.repository.suggestions.SuggestionsRepository
import kotlinx.coroutines.flow.map

class ProductsUseCase(
    private val productRepository: ProductRepository,
    private val suggestionsRepository: SuggestionsRepository
) {
    fun getProductById(productId: String) = productRepository.getProductById(productId)
        .map { product ->
            /**
             * This flow insert the success response id into a table to offer suggestions in next time
             */
            product.categoryId?.let { suggestionsRepository.addSuggestion(it) }
            product
        }
}