package com.dossantos.data.utils

import org.junit.Test

class ConverterUtils {

    @Test
    fun `on double get monetary value`() {
        // Given
        val price = 50.30

        // When
        val currency = price.toCurrency("BRL")

        // Then
        assert(currency == "R\$ 50,30")
    }

    @Test
    fun `replace thumbnail to picture`() {
        // Given
        val thumbUrl = "xpto-I.jpg"
        val pictureUrl = "xpto-O.jpg"

        // When
        val picture = thumbUrl.replaceThumbnailToPicture()

        // Then
        assert(picture == pictureUrl)
    }

    @Test
    fun `erase text`() {
        // Given
        val text = "Text to erase"

        // When
        val erased = text.eraseString()

        // Then
        assert("T̶e̶x̶t̶ ̶t̶o̶ ̶e̶r̶a̶s̶e̶" == erased)
    }

    @Test
    fun `show original price`() {
        // Given
        val originalPrice = 100.00
        val price = 80.0

        // When
        val priceToShow = showOriginalPrice(originalPrice, price)

        // Then
        assert(originalPrice == priceToShow)
    }

    @Test
    fun `dont show original price`() {
        // Given
        val originalPrice = 50.00
        val price = 80.0

        // When
        val priceToShow = showOriginalPrice(originalPrice, price)

        // Then
        assert(null == priceToShow)
    }

    @Test
    fun `calculate discount`() {
        // Given
        val originalPrice = 100.00
        val price = 80.0

        // When
        val discout = calculateDiscount(originalPrice, price)

        // Then
        assert(discout == 20)
    }

    @Test
    fun `format discount in string`() {
        // Given
        val originalPrice = 100.00
        val price = 80.0

        // When
        val discout = calculateDiscount(originalPrice, price)

        // Then
        assert(discout.formatDiscountToString() == "20% OFF")
    }
}