package com.elacqua.albedo.data.remote.jikan_api.model

import com.google.gson.annotations.SerializedName

data class Manga(
    @SerializedName("authors")
    val authors: List<Author>,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("members")
    val members: Int,
    @SerializedName("publishing_start")
    val publishingStart: String?,
    @SerializedName("score")
    val score: Double,
    @SerializedName("synopsis")
    val synopsis: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("volumes")
    val volumes: Int?,
    @SerializedName("published")
    val published: Published = Published()
)