package com.elacqua.albedo.data.remote.jikan_api.model

import com.google.gson.annotations.SerializedName

data class Producer(
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("url")
    val url: String? = null

) {
    override fun toString(): String {
        return "$name "
    }
}