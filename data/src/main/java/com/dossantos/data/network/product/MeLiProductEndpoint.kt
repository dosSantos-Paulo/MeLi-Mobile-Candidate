package com.dossantos.data.network.product

import com.dossantos.data.model.product.ProductResponseDetailDto
import com.dossantos.data.model.product.ProductResponseDto
import com.dossantos.data.utils.MeLiConnector
import com.dossantos.data.utils.Paths
import retrofit2.http.GET
import retrofit2.http.Path

interface MeLiProductEndpoint {

    @GET(Paths.item)
    suspend fun getProductById(
        @Path("item_id") itemId: String
    ): ProductResponseDto

    @GET(Paths.itemDetail)
    suspend fun getProductDetailById(
        @Path("item_id") itemId: String
    ): ProductResponseDetailDto

    companion object {
        val instance: MeLiProductEndpoint by lazy {
            MeLiConnector.getInstance().create(MeLiProductEndpoint::class.java)
        }
    }
}