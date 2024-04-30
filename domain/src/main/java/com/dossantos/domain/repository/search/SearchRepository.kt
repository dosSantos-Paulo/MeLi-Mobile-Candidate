package com.dossantos.domain.repository.search

import com.dossantos.domain.model.search.SearchResponseModel
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchByCategory(categoryId: String): Flow<SearchResponseModel>
    fun searchByCategories(categoriesId: List<String>): Flow<List<SearchResponseModel>>
    fun searchByString(string: String): Flow<SearchResponseModel>
}