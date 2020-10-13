package com.elacqua.albedo.data.remote.jikan_api.service

import com.elacqua.albedo.data.remote.jikan_api.model.Anime
import com.elacqua.albedo.data.remote.jikan_api.model.Manga
import com.elacqua.albedo.data.remote.jikan_api.model.Result
import retrofit2.http.GET

interface TopItemService {

    @GET("top/anime/1/airing")
    suspend fun getTopAiringAnime(): Result<Anime>

    @GET("top/anime/1/upcoming")
    suspend fun getTopUpcomingAnime(): Result<Anime>

    @GET("top/anime/1/movie")
    suspend fun getTopMovies(): Result<Anime>

    @GET("top/manga/1/manga")
    suspend fun getTopManga(): Result<Manga>

    @GET("top/manga/1/novels")
    suspend fun getTopNovels(): Result<Manga>
}