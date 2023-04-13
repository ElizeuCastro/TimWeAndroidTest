package com.example.starwarsmovies.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    private val connectTimeout: Long = 40
    private val readTimeout: Long = 40
    private val BASE_URL = "https://api.npoint.io/"

    private val okHttpClientBuilder = OkHttpClient.Builder()
        .connectTimeout(connectTimeout, TimeUnit.SECONDS)
        .readTimeout(readTimeout, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClientBuilder)
        .build()

    fun getApiService(): StarWarsMoviesApi {
        return retrofit.create(StarWarsMoviesApi::class.java)
    }
}