package com.elacqua.albedo.data.remote.jikan_api.service

import com.elacqua.albedo.data.remote.jikan_api.model.Anime
import com.elacqua.albedo.data.remote.jikan_api.model.AnimeGenre
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeService {

    @GET("anime/{id}")
    suspend fun getAnimeById(@Path("id") id: Int): Anime

    @GET("genre/anime/{id}")
    suspend fun getAnimeByGenreId(@Path("id") id: Int): AnimeGenre

}