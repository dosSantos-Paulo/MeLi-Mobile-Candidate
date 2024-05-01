package com.dossantos.data.repository.search

import com.dossantos.data.model.search.SearchInstallmentsDto
import com.dossantos.data.model.search.SearchPagingInfoDto
import com.dossantos.data.model.search.SearchProductDto
import com.dossantos.data.model.search.SearchResponseDto
import com.dossantos.data.network.search.MeLiSearchEndpoint
import com.dossantos.data.utils.Numbers.Integers.oneHunderd
import com.dossantos.domain.model.InstallmentsModel
import com.dossantos.domain.model.PagingInfoModel
import com.dossantos.domain.model.search.ProductsModel
import com.dossantos.domain.model.search.SearchResponseModel
import com.dossantos.domain.repository.search.SearchRepository
import java.text.NumberFormat
import java.util.Currency
import kotlinx.coroutines.flow.flow
import kotlin.math.roundToInt

class SearchRepositoryImpl(
    private val searchEndpoint: MeLiSearchEndpoint
) : SearchRepository {

    override fun searchByCategory(categoryId: String) = flow {
        emit(searchEndpoint.searchByCategory(categoryId = categoryId).toModel())
    }


    override fun searchByCategories(categoriesId: List<String>) = flow {
        emit(
            categoriesId.map {
                searchEndpoint.searchByCategory(categoryId = it).toModel()
            }
        )
    }

    override fun searchByString(string: String, offset: Int) = flow {
        emit(searchEndpoint.searchByString(string = string, offset = offset).toModel())
    }

    companion object {
        fun SearchResponseDto.toModel() = SearchResponseModel(
            pagingInfo = paging.toModel(),
            products = results?.map { it.toModel() }
        )

        private fun SearchPagingInfoDto?.toModel() = PagingInfoModel(this?.limit, this?.offset)

        private fun SearchProductDto.toModel() = ProductsModel(
            id,
            title,
            price.toCurrency(installments?.currencyId),
            showOriginalPrice().toCurrency(installments?.currencyId).erase(),
            categoryId,
            thumbnail.replaceThumbnailToPicture(),
            installments.toModel(),
            itemDiscount = calculateDiscount()
        )

        private fun SearchInstallmentsDto?.toModel() = InstallmentsModel(this?.currencyId)

        private fun Double?.toCurrency(currencyId: String?): String? {
            if (this == null) return null

            val format: NumberFormat = NumberFormat.getCurrencyInstance()
            format.currency = Currency.getInstance(currencyId?:"BRL")
            return format.format(this)
        }

        private fun SearchProductDto.calculateDiscount(): String? {
            if (originalPrice == null || price == null) return null

            return if (originalPrice > price) {
                "${(((originalPrice - price) / originalPrice) * oneHunderd).roundToInt()}% OFF"
            } else null
        }

        private fun SearchProductDto.showOriginalPrice() =
            if (originalPrice != null && price != null && originalPrice > price) originalPrice
            else null

        private fun String?.erase(): String? {
            if (this == null) return null

            val builder = StringBuilder()
            for (char in this) {
                builder.append(char)
                builder.append("\u0336")
            }
            return builder.toString()
        }

        private fun String?.replaceThumbnailToPicture(): String? {
            if (this == null) return null

            return replace("-I.jpg", "-O.jpg")
        }
    }
}
