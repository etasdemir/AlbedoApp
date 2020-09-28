package com.elacqua.albedo.data.remote.jikan_api.model


import com.google.gson.annotations.SerializedName

data class MangaGenre(
    @SerializedName("item_count")
    val itemCount: Int,
    @SerializedName("mal_url")
    val malUrl: MalUrl,
    @SerializedName("manga")
    val manga: List<Manga>
) {
    data class MalUrl(
        @SerializedName("mal_id")
        val malId: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("type")
        val type: String
    )

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
        val volumes: Int?
    ) {
        data class Author(
            @SerializedName("name")
            val name: String,
            @SerializedName("url")
            val url: String
        )

        data class Genre(
            @SerializedName("mal_id")
            val malId: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("type")
            val type: String
        )
    }
}