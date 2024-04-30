package com.dossantos.domain.repository.category

import com.dossantos.domain.model.category.CategoryModel
import com.dossantos.domain.model.category.MenuCategoryModel
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getMenuCategory(): Flow<List<MenuCategoryModel>>

    fun getCategory(categoryId: String): Flow<CategoryModel>
}
