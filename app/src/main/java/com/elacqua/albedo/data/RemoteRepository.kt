package com.elacqua.albedo.data

import com.elacqua.albedo.data.remote.jikan_api.JikanApiClient
import com.elacqua.albedo.data.remote.jikan_api.model.*
import com.elacqua.albedo.data.remote.jikan_api.model.Search
import com.elacqua.albedo.data.remote.jikan_api.service.AnimeService
import com.elacqua.albedo.data.remote.jikan_api.service.MangaService
import com.elacqua.albedo.data.remote.jikan_api.service.ScheduleService
import com.elacqua.albedo.data.remote.jikan_api.service.SearchService
import com.elacqua.albedo.data.remote.quote_api.Quote
import com.elacqua.albedo.data.remote.quote_api.QuoteRetrofit

class RemoteRepository {
    private val animeService = JikanApiClient.instance.create(AnimeService::class.java)
    private val mangaService = JikanApiClient.instance.create(MangaService::class.java)
    private val scheduleService = JikanApiClient.instance.create(ScheduleService::class.java)
    private val searchService = JikanApiClient.instance.create(SearchService::class.java)
    private val quoteRetrofit = QuoteRetrofit.instance

    suspend fun getQuote(): Quote {
        return quoteRetrofit.getRandomQuote()
    }

    suspend fun getMultipleAnimeByGenreId(id: IntArray): ArrayList<AnimeGenre> {
        val result = ArrayList<AnimeGenre>()
        id.forEach {
            result.add(animeService.getAnimeByGenreId(it))
        }
        return result
    }

    suspend fun getAnimeByGenreId(id: Int): AnimeGenre {
        return animeService.getAnimeByGenreId(id)
    }

    suspend fun getAnimeById(id: Int): Anime {
        return animeService.getAnimeById(id)
    }

    suspend fun getMultipleMangaByGenreId(id: IntArray): ArrayList<MangaGenre> {
        val result = ArrayList<MangaGenre>()
        id.forEach {
            result.add(mangaService.getMangaByGenreId(it))
        }
        return result
    }

    suspend fun getMangaByGenreId(id: Int): MangaGenre {
        return mangaService.getMangaByGenreId(id)
    }

    suspend fun getMangaById(id: Int): Manga {
        return mangaService.getMangaById(id)
    }

    /**
     *  Note: remove airingStart > current year is only temporary solution.
     *
     *  it.airingStart.split("-")[0] > "2019"
     * */
    suspend fun getScheduleAllDays(): Schedule {
        val result = scheduleService.getScheduleAllDays()
        return result
    }

    suspend fun getScheduleSingleDay(day: String): List<Anime> {
        return scheduleService.getScheduleSingleDay(day)
    }

    suspend fun getMostPopularAnime(): Top<Anime> {
        val result = searchService.getMostPopularAnime()
        return result
    }

    suspend fun getSearchResultAnime(searchType: String, query: String): Search<Anime> {
        return searchService.getSearchResultAnime(searchType, query, 1)
    }

    suspend fun getSearchResultManga(searchType: String, query: String): Search<Manga> {
        return searchService.getSearchResultManga(searchType, query, 1)
    }

    suspend fun getSearchResultPeople(searchType: String, query: String): Search<People> {
        return searchService.getSearchResultPeople(searchType, query, 1)
    }

    suspend fun getSearchResultCharacter(searchType: String, query: String): Search<Character> {
        return searchService.getSearchResultCharacter(searchType, query, 1)
    }
}
