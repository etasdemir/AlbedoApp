package com.elacqua.albedo.data

import com.elacqua.albedo.data.remote.jikan_api.JikanApiClient
import com.elacqua.albedo.data.remote.jikan_api.model.AnimeGenre
import com.elacqua.albedo.data.remote.jikan_api.model.MangaGenre
import com.elacqua.albedo.data.remote.quote_api.Quote
import com.elacqua.albedo.data.remote.quote_api.QuoteRetrofit
import kotlinx.coroutines.Dispatchers

class RemoteRepository{
    private val jikanRetrofit = JikanApiClient.instance
    private val quoteRetrofit = QuoteRetrofit.instance

    suspend fun getQuote(): Quote {
        return quoteRetrofit.getRandomQuote()
    }

    suspend fun getMultipleAnimeByGenreId(id: IntArray): ArrayList<AnimeGenre> {
        val result = ArrayList<AnimeGenre>()
        id.forEach {
            result.add(jikanRetrofit.getAnimeByGenreId(it))
        }
        return result
    }

    suspend fun getAnimeByGenreId(id: Int): AnimeGenre {
        return jikanRetrofit.getAnimeByGenreId(id)
    }

    suspend fun getMultipleMangaByGenreId(id: IntArray): ArrayList<MangaGenre> {
        val result = ArrayList<MangaGenre>()
        id.forEach {
            result.add(jikanRetrofit.getMangaByGenreId(it))
        }
        return result
    }

    suspend fun getMangaByGenreId(id: Int): MangaGenre {
        return jikanRetrofit.getMangaByGenreId(id)
    }
}