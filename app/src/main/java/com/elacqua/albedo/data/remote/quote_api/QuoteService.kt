package com.elacqua.albedo.data.remote.quote_api

import retrofit2.Call
import retrofit2.http.GET

interface QuoteService {
    @GET("quotes/random")
    suspend fun getRandomQuote(): Quote
}