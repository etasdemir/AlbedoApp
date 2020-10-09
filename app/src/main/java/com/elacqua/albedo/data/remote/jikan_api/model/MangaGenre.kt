package com.elacqua.albedo.data.remote.jikan_api.model


import com.google.gson.annotations.SerializedName

data class MangaGenre(
    @SerializedName("mal_url")
    val malUrl: MalUrl,
    @SerializedName("manga")
    val manga: List<Manga> = emptyList(),
    @SerializedName("item_count")
    val itemCount: Int = manga.size,
)
