package com.dossantos.data.model.search

data class SearchResponseDto (
    val paging: SearchPagingInfoDto?,
    val results: List<SearchProductDto>?
)