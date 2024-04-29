package com.dossantos.data.model.category

data class MeLiCategoryDao(
    val id: String,
    val name: String,
    val picture: String,
    val permalink: String,
    val total_items_in_this_category: Long,
    val path_from_root: List<MeLiCategoryPathFromRootDto>,
    val children_categories: List<MeLiCategoryChildCategoryDto>,
    val attribute_types: String,
    val settings: MeLiCategorySettingsDto,
    val channels_settings: List<MeLiCategoryChannelSettingDto>,
    val meta_categ_id: Any?,
    val attributable: Boolean,
    val date_created: String
)

