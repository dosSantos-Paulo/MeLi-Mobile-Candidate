package com.dossantos.domain.repository.product

import com.dossantos.domain.model.product.ProductResponseModel
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProductById(productId: String): Flow<ProductResponseModel>
}
