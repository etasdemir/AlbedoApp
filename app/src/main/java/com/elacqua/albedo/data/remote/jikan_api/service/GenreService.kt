package com.elacqua.albedo.data.remote.jikan_api.service

import com.elacqua.albedo.data.remote.jikan_api.model.AnimeGenre
import com.elacqua.albedo.data.remote.jikan_api.model.MangaGenre
import retrofit2.http.GET
import retrofit2.http.Path

interface GenreService {

    @GET("genre/anime/{id}")
    suspend fun getAnimeByGenreId(@Path("id") id: Int): AnimeGenre

    @GET("genre/manga/{id}")
    suspend fun getMangaByGenreId(@Path("id") id: Int): MangaGenre
}