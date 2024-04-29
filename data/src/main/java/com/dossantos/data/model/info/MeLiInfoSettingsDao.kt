package com.dossantos.data.model.info

data class MeLiInfoSettingsDao(
    val identification_types: List<String>,
    val taxpayer_types: List<Any?>?,
    val identification_types_rules: Any?
)
