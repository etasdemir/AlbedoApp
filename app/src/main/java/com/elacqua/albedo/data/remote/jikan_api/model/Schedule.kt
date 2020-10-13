package com.elacqua.albedo.data.remote.jikan_api.model


import com.google.gson.annotations.SerializedName

data class Schedule(
    @SerializedName("monday")
    val monday: List<Anime> = emptyList(),
    @SerializedName("tuesday")
    val tuesday: List<Anime> = emptyList(),
    @SerializedName("wednesday")
    val wednesday: List<Anime> = emptyList(),
    @SerializedName("thursday")
    val thursday: List<Anime> = emptyList(),
    @SerializedName("friday")
    val friday: List<Anime> = emptyList(),
    @SerializedName("saturday")
    val saturday: List<Anime> = emptyList(),
    @SerializedName("sunday")
    val sunday: List<Anime> = emptyList(),
)

