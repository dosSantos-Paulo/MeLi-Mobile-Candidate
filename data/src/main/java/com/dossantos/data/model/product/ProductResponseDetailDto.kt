package com.dossantos.data.model.product

import com.google.gson.annotations.SerializedName

data class ProductResponseDetailDto(
    @SerializedName("plain_text")
    val productDescription: String?
)
