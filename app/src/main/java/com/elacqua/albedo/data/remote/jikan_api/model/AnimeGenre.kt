package com.elacqua.albedo.data.remote.jikan_api.model


import com.google.gson.annotations.SerializedName

data class AnimeGenre(
    @SerializedName("anime")
    val anime: List<Anime> = emptyList(),
    @SerializedName("item_count")
    val itemCount: Int = anime.size,
    @SerializedName("mal_url")
    val malUrl: MalUrl = MalUrl()
)