package com.elacqua.albedo.data.remote.jikan_api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object JikanApiClient {
    private const val BASE_URL = "https://api.jikan.moe/v3/"

    val instance = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}