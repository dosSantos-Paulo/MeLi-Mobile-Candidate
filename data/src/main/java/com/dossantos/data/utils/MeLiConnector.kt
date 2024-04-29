package com.dossantos.data.utils

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object MeLiConnector {
    private const val BASE_URL = "https://api.mercadolibre.com/"

    private const val AUTHORIZATION = "Authorization"

    private const val AUTH_KEY =
        "Basic Xpto_NotNecessary_"

    private const val TIME_OUT = 30L

    private val interceptor = Interceptor { chain ->
        chain.proceed(
            chain.request()
                .newBuilder()
                .addHeader(AUTHORIZATION, AUTH_KEY)
                .build()
        )
    }

    private var instance: Retrofit? = null

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .build()

    fun getInstance() = instance ?: Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .also { instance = it }
}