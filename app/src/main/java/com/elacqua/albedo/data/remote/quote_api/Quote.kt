package com.elacqua.albedo.data.remote.quote_api


import com.google.gson.annotations.SerializedName

data class Quote(
    @SerializedName("data")
    var data: List<Data> = emptyList()
) {
    data class Data(
        @SerializedName("anime")
        var anime: String = "",
        @SerializedName("character")
        var character: String = "",
        @SerializedName("quote")
        var quote: String = ""
    )
}