package com.elacqua.albedo.data.remote.jikan_api.service

import com.elacqua.albedo.data.remote.jikan_api.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchService {

    @GET("top/anime/1/bypopularity")
    suspend fun getMostPopularAnime(): Top<Anime>

    @GET("search/{type}")
    suspend fun getSearchResultAnime(@Path("type") type: String,
                                     @Query("q") query: String,
                                     @Query("page") page: Int)
            : Result<Anime>

    @GET("search/{type}")
    suspend fun getSearchResultManga(@Path("type") type: String,
                                     @Query("q") query: String,
                                     @Query("page") page: Int)
            : Result<Manga>

    @GET("search/{type}")
    suspend fun getSearchResultPeople(@Path("type") type: String,
                                      @Query("q") query: String,
                                      @Query("page") page: Int)
            : Result<People>

    @GET("search/{type}")
    suspend fun getSearchResultCharacter(@Path("type") type: String,
                                         @Query("q") query: String,
                                         @Query("page") page: Int)
            : Result<Character>

}