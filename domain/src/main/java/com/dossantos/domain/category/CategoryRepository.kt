package com.dossantos.domain.category

import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategory(): Flow<List<CategoryEntity>>
}