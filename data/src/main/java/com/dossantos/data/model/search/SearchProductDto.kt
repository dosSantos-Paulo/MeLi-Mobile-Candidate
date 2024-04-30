package com.dossantos.data.model.search

import com.google.gson.annotations.SerializedName

data class SearchProductDto(
    val id: String?,
    val title: String?,
    val price: Double?,
    @SerializedName("original_price")
    val originalPrice: Double?,
    @SerializedName("category_id")
    val categoryId: String?,
    val thumbnail: String?,
    val installments: SearchInstallmentsDto?
)
