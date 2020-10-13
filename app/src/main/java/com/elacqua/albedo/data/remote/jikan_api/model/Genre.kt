package com.elacqua.albedo.data.remote.jikan_api.model

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String


) {
    override fun toString(): String {
        return "$name "
    }
}