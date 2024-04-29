package com.dossantos.data.network.info

import com.dossantos.data.utils.MeLiConnector
import com.dossantos.data.utils.Paths
import com.dossantos.data.model.info.MeLiInfoDto
import retrofit2.http.GET

interface MeLiBrInfoEndpoint {

    @GET(Paths.mlbInfo)
    suspend fun getMlbInfo(): MeLiInfoDto

    companion object {
        val instance: MeLiBrInfoEndpoint by lazy {
            MeLiConnector.getInstance().create(MeLiBrInfoEndpoint::class.java)
        }
    }
}