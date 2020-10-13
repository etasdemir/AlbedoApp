package com.elacqua.albedo.data.remote.jikan_api.model

import com.google.gson.annotations.SerializedName

data class People(
    @SerializedName("alternative_names")
    val alternativeNames: List<String> = listOf(),
    @SerializedName("image_url")
    val imageUrl: String = "",
    @SerializedName("mal_id")
    val malId: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("url")
    val url: String = ""
)