package com.dossantos.data.repository.category

import com.dossantos.data.network.category.MeLiCategoryEndpoint
import com.dossantos.data.network.info.MeLiBrInfoEndpoint
import com.dossantos.domain.model.category.CategoryModel
import com.dossantos.domain.model.category.MenuCategoryModel
import com.dossantos.domain.repository.category.CategoryRepository
import kotlinx.coroutines.flow.flow

class CategoryRepositoryImpl(
    val categoryEndpoint: MeLiCategoryEndpoint,
    val infoEndpoint: MeLiBrInfoEndpoint
) : CategoryRepository {

    override fun getMenuCategory() = flow {
        val responseList = mutableListOf<MenuCategoryModel>()

        infoEndpoint.getMlbInfo().categories.forEach { categoryDao ->
            responseList.add(
                MenuCategoryModel(
                    categoryId = categoryDao.id,
                    categoryName = categoryDao.name,
                    categoryImage = categoryEndpoint.getCategory(categoryDao.id).picture
                )
            )

        }

        emit(responseList)
    }

    override fun getCategory(categoryId: String) = flow {
        emit(CategoryModel(categoryEndpoint.getCategory(categoryId).id))
    }
}
