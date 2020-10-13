package com.elacqua.albedo.data.remote.jikan_api.model

import com.google.gson.annotations.SerializedName

data class Anime(
    @SerializedName("mal_id")
    val malId: Int = 0,
    @SerializedName("airing_start")
    val airingStart: String = "",
    @SerializedName("episodes")
    val episodes: Int = 0,
    @SerializedName("genres")
    val genres: List<Genre> = listOf(),
    @SerializedName("image_url")
    val imageUrl: String = "",
    @SerializedName("members")
    val members: Int = 0,
    @SerializedName("producers")
    val producers: List<Producer> = listOf(),
    @SerializedName("score")
    val score: Double = 0.0,
    @SerializedName("source")
    val source: String = "",
    @SerializedName("synopsis")
    val synopsis: String = "",
    @SerializedName(value = "title", alternate = ["name"])
    val title: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("trailer_url")
    val trailerUrl: String = "",
    @SerializedName("aired")
    val published: Published = Published(),
    @SerializedName("opening_themes")
    val openingThemes: List<String> = emptyList(),
    @SerializedName("ending_themes")
    val endingThemes: List<String> = emptyList()

)

