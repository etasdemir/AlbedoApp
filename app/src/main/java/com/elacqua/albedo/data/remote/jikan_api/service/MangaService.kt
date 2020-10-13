package com.elacqua.albedo.data.remote.jikan_api.service

import com.elacqua.albedo.data.remote.jikan_api.model.Manga
import com.elacqua.albedo.data.remote.jikan_api.model.MangaGenre
import retrofit2.http.GET
import retrofit2.http.Path

interface MangaService {

    @GET("manga/{id}")
    suspend fun getMangaById(@Path("id") id: Int): Manga

    @GET("genre/manga/{id}")
    suspend fun getMangaByGenreId(@Path("id") id: Int): MangaGenre

}