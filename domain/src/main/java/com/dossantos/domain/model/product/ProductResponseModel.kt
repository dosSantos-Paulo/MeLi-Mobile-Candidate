package com.dossantos.domain.model.product

data class ProductResponseModel(
    val id: String?,
    val title: String?,
    val categoryId: String?,
    val price: String?,
    val originalPrice: String?,
    val discountAmount: String?,
    val currencyId: String?,
    val permalink: String?,
    val picturesUrl: List<String?>?,
    val warranty: String?,
    val productDescription: String?
)
