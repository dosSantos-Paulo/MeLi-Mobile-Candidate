package com.dossantos.domain.repository.category

import com.dossantos.domain.model.category.CategoryEntity
import com.dossantos.domain.model.category.MenuCategoryEntity
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getMenuCategory(): Flow<List<MenuCategoryEntity>>

    fun getCategory(categoryId: String): Flow<CategoryEntity>
}
