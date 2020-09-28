package com.elacqua.albedo.data.remote.quote_api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object QuoteRetrofit {
    private val BASE_URL = "https://animechanapi.xyz/api/"

    val instance = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(QuoteService::class.java)
}