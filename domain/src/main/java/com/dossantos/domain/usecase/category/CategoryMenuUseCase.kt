package com.dossantos.domain.usecase.category

import com.dossantos.domain.repository.category.CategoryRepository

class CategoryMenuUseCase(private val categoryRepository: CategoryRepository) {
    fun getMenuCategory() = categoryRepository.getMenuCategory()
}
