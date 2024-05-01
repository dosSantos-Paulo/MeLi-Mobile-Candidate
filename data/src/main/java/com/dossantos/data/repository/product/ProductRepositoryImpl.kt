package com.dossantos.data.repository.product

import com.dossantos.data.model.product.ProductResponseDto
import com.dossantos.data.network.product.MeLiProductEndpoint
import com.dossantos.data.utils.calculateDiscount
import com.dossantos.data.utils.formatDiscountToString
import com.dossantos.data.utils.showOriginalPrice
import com.dossantos.data.utils.toCurrency
import com.dossantos.domain.model.product.ProductResponseModel
import com.dossantos.domain.repository.product.ProductRepository
import kotlinx.coroutines.flow.flow

class ProductRepositoryImpl(
    private val productEndpoint: MeLiProductEndpoint
) : ProductRepository {

    override fun getProductById(productId: String) = flow {
        emit(
            productEndpoint
                .getProductById(productId)
                .toModel(productEndpoint.getProductDetailById(productId).productDescription)
        )
    }

    private fun ProductResponseDto.toModel(productDescription: String?) = ProductResponseModel(
        id = id,
        title = title,
        categoryId = categoryId,
        price = price.toCurrency(currencyId),
        originalPrice = showOriginalPrice(originalPrice, price).toCurrency(currencyId),
        discountAmount = calculateDiscount(originalPrice, price).formatDiscountToString(),
        currencyId = currencyId,
        permalink = permalink,
        picturesUrl = pictures?.map { it.url },
        warranty = warranty,
        productDescription = productDescription
    )

}
