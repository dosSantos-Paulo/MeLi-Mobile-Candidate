package com.dossantos.data.network.category

import com.dossantos.data.model.category.MeLiCategoryDto
import com.dossantos.data.utils.MeLiConnector
import com.dossantos.data.utils.Paths
import retrofit2.http.GET
import retrofit2.http.Path

interface MeLiCategoryEndpoint {

    @GET(Paths.category + "{categoryId}")
    suspend fun getCategory(
        @Path("categoryId") categoryId: String
    ): MeLiCategoryDto

    companion object {
        val instance: MeLiCategoryEndpoint by lazy {
            MeLiConnector.getInstance().create(MeLiCategoryEndpoint::class.java)
        }
    }
}
