package com.elacqua.albedo.data.remote.jikan_api.model

import com.google.gson.annotations.SerializedName

data class Top<T>(
    @SerializedName("top")
    val results: List<T> = listOf()
)