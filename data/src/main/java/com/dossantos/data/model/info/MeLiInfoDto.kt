package com.dossantos.data.model.info

import com.google.gson.annotations.SerializedName

data class MeLiInfoDto(
    val id: String,
    val name: String,
    @SerializedName("country_id")
    val countryId: String,
    @SerializedName("sale_fees_mode")
    val saleFeesMode: String,
    @SerializedName("mercadopago_version")
    val mercadoPagoVersion: Int,
    @SerializedName("default_currency_id")
    val defaultCurrencyId: String,
    @SerializedName("immediate_payment")
    val immediatePayment: String,
    @SerializedName("payment_method_ids")
    val paymentMethodIds: List<String>,
    val settings: MeLiInfoSettingsDto,
    val currencies: List<MeLiInfoCurrencyDto>,
    val categories: List<MeLiInfoCategoryDto>,
    val channels: List<String>
)
