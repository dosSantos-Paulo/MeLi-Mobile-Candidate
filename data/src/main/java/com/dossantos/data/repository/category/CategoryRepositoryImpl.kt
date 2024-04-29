package com.dossantos.data.repository.category

import com.dossantos.data.network.category.MeLiCategoryEndpoint
import com.dossantos.data.network.info.MeLiBrInfoEndpoint
import com.dossantos.domain.model.category.CategoryEntity
import com.dossantos.domain.model.category.MenuCategoryEntity
import com.dossantos.domain.repository.category.CategoryRepository
import kotlinx.coroutines.flow.flow

class CategoryRepositoryImpl(
    val categoryEndpoint: MeLiCategoryEndpoint,
    val infoEndpoint: MeLiBrInfoEndpoint
) : CategoryRepository {

    override fun getMenuCategory() = flow {
        val responseList = mutableListOf<MenuCategoryEntity>()

        infoEndpoint.getMlbInfo().categories.forEach { categoryDao ->
            responseList.add(
                MenuCategoryEntity(
                    categoryId = categoryDao.id,
                    categoryName = categoryDao.name,
                    categoryImage = categoryEndpoint.getCategory(categoryDao.id).picture
                )
            )

        }

        emit(responseList)
    }

    override fun getCategory(categoryId: String) = flow {
        emit(CategoryEntity(categoryEndpoint.getCategory(categoryId).id))
    }
}
