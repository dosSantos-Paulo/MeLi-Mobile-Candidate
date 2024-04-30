package com.dossantos.domain.model.search

import com.dossantos.domain.model.InstallmentsModel

data class ProductsModel(
    val id: String?,
    val title: String?,
    val price: String?,
    val originalPrice: String?,
    val categoryId: String?,
    val thumbnail: String?,
    val installments: InstallmentsModel?,
    val itemDiscount: String?
)
