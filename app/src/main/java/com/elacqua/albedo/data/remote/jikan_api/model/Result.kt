package com.elacqua.albedo.data.remote.jikan_api.model


import com.google.gson.annotations.SerializedName

data class Result<T>(
    @SerializedName(value = "results", alternate = ["top"])
    val results: List<T> = listOf()
)