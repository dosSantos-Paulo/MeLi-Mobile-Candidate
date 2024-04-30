package com.dossantos.data.model.category

import com.google.gson.annotations.SerializedName

data class MeLiCategoryDao(
    val id: String,
    val name: String,
    val picture: String,
    val permalink: String,
    @SerializedName("total_items_in_this_category")
    val totalItemsInThisCategory: Long,
    @SerializedName("path_from_root")
    val pathFromRoot: List<MeLiCategoryPathFromRootDto>,
    @SerializedName("children_categories")
    val childrenCategories: List<MeLiCategoryChildCategoryDto>,
    @SerializedName("attribute_types")
    val attributeTypes: String,
    val settings: MeLiCategorySettingsDto,
    @SerializedName("channels_settings")
    val channelsSettings: List<MeLiCategoryChannelSettingDto>,
    @SerializedName("meta_categ_id")
    val metaCategId: Any?,
    val attributable: Boolean,
    @SerializedName("date_created")
    val dateCreated: String
)
