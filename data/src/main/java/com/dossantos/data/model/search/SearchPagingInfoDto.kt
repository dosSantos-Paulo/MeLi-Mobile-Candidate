package com.dossantos.data.model.search

import com.google.gson.annotations.SerializedName

data class SearchPagingInfoDto(
    val total: Int?,
    @SerializedName("primary_results")
    val primaryResults: Int?,
    val offset: Int?,
    val limit: Int?
)
