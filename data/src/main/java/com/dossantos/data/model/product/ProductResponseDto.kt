package com.dossantos.data.model.product

import com.google.gson.annotations.SerializedName

data class ProductResponseDto(
    val id: String?,
    val title: String?,
    @SerializedName("category_id")
    val categoryId: String?,
    val price: Double?,
    @SerializedName("original_price")
    val originalPrice: Double?,
    @SerializedName("currency_id")
    val currencyId: String?,
    val permalink: String?,
    val pictures: List<ProductPicturesDto>?,
    val warranty: String?,
)
