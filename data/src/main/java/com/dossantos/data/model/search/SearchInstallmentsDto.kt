package com.dossantos.data.model.search

import com.google.gson.annotations.SerializedName

data class SearchInstallmentsDto(
    @SerializedName("currency_id")
    val currencyId: String?
)
