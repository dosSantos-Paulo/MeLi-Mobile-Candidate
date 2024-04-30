package com.dossantos.data.model.category

import com.google.gson.annotations.SerializedName

data class MeLiCategoryChildCategoryDto(
    val id: String,
    val name: String,
    @SerializedName("total_items_in_this_category")
    val totalItemsInThisCategory: Long
)
