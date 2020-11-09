package com.elacqua.albedo.data.remote.jikan_api.model

import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
) {
    override fun toString(): String {
        return name
    }
}