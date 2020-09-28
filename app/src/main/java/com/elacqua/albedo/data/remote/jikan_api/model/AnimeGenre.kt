package com.elacqua.albedo.data.remote.jikan_api.model


import com.google.gson.annotations.SerializedName

data class AnimeGenre(
    @SerializedName("anime")
    val anime: List<Anime> = emptyList(),
    @SerializedName("item_count")
    val itemCount: Int? = null,
    @SerializedName("mal_url")
    val malUrl: MalUrl? = null
) {
    data class Anime(
        @SerializedName("airing_start")
        val airingStart: String? = null,
        @SerializedName("episodes")
        val episodes: Int? = null,
        @SerializedName("genres")
        val genres: List<Genre> = emptyList(),
        @SerializedName("image_url")
        val imageUrl: String? = null,
        @SerializedName("members")
        val members: Int? = null,
        @SerializedName("producers")
        val producers: List<Producer> = emptyList(),
        @SerializedName("score")
        val score: Double? = null,
        @SerializedName("source")
        val source: String? = null,
        @SerializedName("synopsis")
        val synopsis: String? = null,
        @SerializedName("title")
        val title: String? = null,
        @SerializedName("url")
        val url: String? = null
    ) {
        data class Genre(
            @SerializedName("mal_id")
            val malId: Int? = null,
            @SerializedName("name")
            val name: String? = null,
            @SerializedName("type")
            val type: String? = null
        )

        data class Producer(
            @SerializedName("name")
            val name: String? = null,
            @SerializedName("url")
            val url: String? = null
        )
    }

    data class MalUrl(
        @SerializedName("mal_id")
        val malId: Int? = null,
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("type")
        val type: String? = null
    )
}