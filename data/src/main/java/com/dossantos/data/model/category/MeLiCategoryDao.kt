package com.dossantos.data.model.category

import com.google.gson.annotations.SerializedName

data class MeLiCategoryDao(
    val id: String,
    val name: String,
    val picture: String,
    @SerializedName("children_categories")
    val childrenCategories: List<MeLiCategoryChildCategoryDto>
)
