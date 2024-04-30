package com.dossantos.data.model.category

import com.google.gson.annotations.SerializedName

data class MeLiCategorySettingsDto(
    @SerializedName("adult_content")
    val adultContent: Boolean,
    @SerializedName("buying_allowed")
    val buyingAllowed: Boolean,
    @SerializedName("buying_modes")
    val buyingModes: List<String>,
    @SerializedName("catalog_domain")
    val catalogDomain: String,
    @SerializedName("coverage_areas")
    val coverageAreas: String,
    val currencies: List<String>,
    val fragile: Boolean,
    @SerializedName("immediate_payment")
    val immediatePayment: String,
    @SerializedName("item_conditions")
    val itemConditions: List<String>,
    @SerializedName("items_reviews_allowed")
    val itemsReviewsAllowed: Boolean,
    @SerializedName("listing_allowed")
    val listingAllowed: Boolean,
    @SerializedName("max_description_length")
    val maxDescriptionLength: Int,
    @SerializedName("max_pictures_per_item")
    val maxPicturesPerItem: Int,
    @SerializedName("max_pictures_per_item_var")
    val maxPicturesPerItemVar: Int,
    @SerializedName("max_sub_title_length")
    val maxSubTitleLength: Int,
    @SerializedName("max_title_length")
    val maxTitleLength: Int,
    @SerializedName("max_variations_allowed")
    val maxVariationsAllowed: Int,
    @SerializedName("maximum_price")
    val maximumPrice: Any?,
    @SerializedName("maximum_price_currency")
    val maximumPriceCurrency: String,
    @SerializedName("minimum_price")
    val minimumPrice: Int,
    @SerializedName("minimum_price_currency")
    val minimumPriceCurrency: String,
    @SerializedName("mirror_category")
    val mirrorCategory: Any?,
    @SerializedName("mirror_master_category")
    val mirrorMasterCategory: Any?,
    @SerializedName("mirror_slave_categories")
    val mirrorSlaveCategories: List<Any>,
    val price: String,
    @SerializedName("reservation_allowed")
    val reservationAllowed: String,
    val restrictions: List<Any>,
    @SerializedName("rounded_address")
    val roundedAddress: Boolean,
    @SerializedName("seller_contact")
    val sellerContact: String,
    @SerializedName("shipping_options")
    val shippingOptions: List<String>,
    @SerializedName("shipping_profile")
    val shippingProfile: String,
    @SerializedName("show_contact_information")
    val showContactInformation: Boolean,
    @SerializedName("simple_shipping")
    val simpleShipping: String,
    val stock: String,
    @SerializedName("sub_vertical")
    val subVertical: Any?,
    val subscribable: Boolean,
    val tags: List<Any>,
    val vertical: Any?,
    @SerializedName("vip_subdomain")
    val vipSubdomain: String,
    @SerializedName("buyer_protection_programs")
    val buyerProtectionPrograms: List<String>,
    val status: String
)
