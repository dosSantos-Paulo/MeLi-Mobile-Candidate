package com.dossantos.domain.model.search

import com.dossantos.domain.model.PagingInfoModel

data class SearchResponseModel(
    val pagingInfo: PagingInfoModel?,
    val products: List<ProductsModel>?
)
