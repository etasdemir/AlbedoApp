package com.elacqua.albedo.data.remote.jikan_api.model


import com.google.gson.annotations.SerializedName

data class MangaGenre(
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("item_count")
    val itemCount: Int,
    @SerializedName("mal_url")
    val malUrl: MalUrl,
    @SerializedName("manga")
    val manga: List<Manga>
)
