package com.dossantos.data.network.search

import com.dossantos.data.model.search.SearchResponseDto
import com.dossantos.data.utils.MeLiConnector
import com.dossantos.data.utils.Paths
import retrofit2.http.GET
import retrofit2.http.Query

interface MeLiSearchEndpoint {

    @GET(Paths.search)
    suspend fun searchByString(
        @Query("status") status: String = "active",
        @Query("q") string: String,
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0
    ): SearchResponseDto

    @GET(Paths.search)
    suspend fun searchByCategory(
        @Query("status") status: String = "active",
        @Query("category") categoryId: String,
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0
    ): SearchResponseDto

    companion object {
        val instance: MeLiSearchEndpoint by lazy {
            MeLiConnector.getInstance().create(MeLiSearchEndpoint::class.java)
        }
    }
}