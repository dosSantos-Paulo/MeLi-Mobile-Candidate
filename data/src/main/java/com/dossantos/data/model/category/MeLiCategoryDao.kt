package com.dossantos.data.model.category

data class MeLiCategoryDao(
    val id: String,
    val name: String,
    val picture: String,
    val permalink: String,
    val total_items_in_this_category: Long,
    val path_from_root: List<MeLiCategoryPathFromRootDao>,
    val children_categories: List<MeLiCategoryChildCategoryDao>,
    val attribute_types: String,
    val settings: MeLiCategorySettingsDao,
    val channels_settings: List<MeLiCategoryChannelSettingDao>,
    val meta_categ_id: Any?,
    val attributable: Boolean,
    val date_created: String
)

