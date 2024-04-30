package com.dossantos.data.model.category

import com.google.gson.annotations.SerializedName

data class MeLiCategoryChannelSettingsSettingsDto(
    @SerializedName("minimum_price")
    val minimumPrice: Int?,
    val status: String?
)
