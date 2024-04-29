package com.dossantos.data.repository.category

import com.dossantos.domain.category.CategoryRepository
import com.dossantos.domain.category.CategoryEntity
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl(): CategoryRepository {

    override fun getCategory(): Flow<List<CategoryEntity>> {
        TODO("Not yet implemented")
    }
}
