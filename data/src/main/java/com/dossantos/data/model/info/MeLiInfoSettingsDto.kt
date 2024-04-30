package com.dossantos.data.model.info

import com.google.gson.annotations.SerializedName

data class MeLiInfoSettingsDto(
    @SerializedName("identification_types")
    val identificationTypes: List<String>,
    @SerializedName("taxpayer_types")
    val taxpayerTypes: List<Any?>?,
    @SerializedName("identification_types_rules")
    val identificationTypesRules: Any?
)
