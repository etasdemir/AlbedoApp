package com.elacqua.albedo.data.remote.quote_api


import com.google.gson.annotations.SerializedName

data class QuoteList(
    @SerializedName("data")
    var data: List<Quote> = emptyList()
)