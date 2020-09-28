package com.elacqua.albedo.data.remote.jikan_api

import com.elacqua.albedo.data.remote.jikan_api.service.GenreService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object JikanApiClient {
    private val BASE_URL = "https://api.jikan.moe/v3/"

    val instance = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GenreService::class.java)
}