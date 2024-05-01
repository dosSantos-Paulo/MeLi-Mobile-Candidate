package com.dossantos.data.utils

import java.text.NumberFormat
import java.util.Currency
import kotlin.math.roundToInt

fun Double?.toCurrency(currencyId: String?): String? {
    if (this == null) return null

    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.currency = Currency.getInstance(currencyId ?: "BRL")
    return format.format(this)
}

fun String?.replaceThumbnailToPicture(): String? {
    if (this == null) return null
    return replace("-I.jpg", "-O.jpg")
}

fun String?.eraseString(): String? {
    if (this == null) return null

    val builder = StringBuilder()
    for (char in this) {
        builder.append(char)
        builder.append("\u0336")
    }
    return builder.toString()
}

fun showOriginalPrice(originalPrice: Double?, price: Double?) =
    if (originalPrice != null && price != null && originalPrice > price) originalPrice
    else null

fun calculateDiscount(originalPrice: Double?, price: Double?): Int? {
    if (originalPrice == null || price == null) return null

    return if (originalPrice > price) {
        (((originalPrice - price) / originalPrice) * Numbers.Integers.oneHunderd).roundToInt()
    } else null
}

fun Int?.formatDiscountToString() = if (this == null) null else "$this% OFF"
