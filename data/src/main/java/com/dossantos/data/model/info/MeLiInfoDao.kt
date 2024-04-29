package com.dossantos.data.model.info

data class MeLiInfoDao(
    val id: String,
    val name: String,
    val country_id: String,
    val sale_fees_mode: String,
    val mercadopago_version: Int,
    val default_currency_id: String,
    val immediate_payment: String,
    val payment_method_ids: List<String>,
    val settings: MeLiInfoSettingsDao,
    val currencies: List<MeLiInfoCurrencyDao>,
    val categories: List<MeLiInfoCategoryDao>,
    val channels: List<String>
)