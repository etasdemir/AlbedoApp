package com.elacqua.albedo.data.remote.quote_api

import com.google.gson.annotations.SerializedName

data class Quote(
    @SerializedName("anime")
    val anime: String = "",
    @SerializedName("character")
    val character: String = "",
    @SerializedName("quote")
    val quote: String = "",
    var isFavourite: Boolean = false
)