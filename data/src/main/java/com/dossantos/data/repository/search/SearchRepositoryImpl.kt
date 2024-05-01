package com.dossantos.data.repository.search

import com.dossantos.data.model.search.SearchInstallmentsDto
import com.dossantos.data.model.search.SearchPagingInfoDto
import com.dossantos.data.model.search.SearchProductDto
import com.dossantos.data.model.search.SearchResponseDto
import com.dossantos.data.network.search.MeLiSearchEndpoint
import com.dossantos.data.utils.Numbers.Integers.oneHunderd
import com.dossantos.data.utils.calculateDiscount
import com.dossantos.data.utils.eraseString
import com.dossantos.data.utils.formatDiscountToString
import com.dossantos.data.utils.replaceThumbnailToPicture
import com.dossantos.data.utils.showOriginalPrice
import com.dossantos.data.utils.toCurrency
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
            id = id,
            title = title,
            price = price.toCurrency(installments?.currencyId),
            originalPrice = showOriginalPrice(originalPrice, price).toCurrency(installments?.currencyId).eraseString(),
            categoryId = categoryId,
            thumbnail = thumbnail.replaceThumbnailToPicture(),
            installments = installments.toModel(),
            itemDiscount = calculateDiscount(originalPrice, price).formatDiscountToString()
        )

        private fun SearchInstallmentsDto?.toModel() = InstallmentsModel(this?.currencyId)
    }
}
