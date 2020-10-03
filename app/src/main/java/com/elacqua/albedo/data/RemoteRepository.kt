package com.elacqua.albedo.data

import com.elacqua.albedo.data.remote.jikan_api.JikanApiClient
import com.elacqua.albedo.data.remote.jikan_api.model.Anime
import com.elacqua.albedo.data.remote.jikan_api.model.AnimeGenre
import com.elacqua.albedo.data.remote.jikan_api.model.MangaGenre
import com.elacqua.albedo.data.remote.jikan_api.model.Schedule
import com.elacqua.albedo.data.remote.jikan_api.model.search.AnimeSearch
import com.elacqua.albedo.data.remote.jikan_api.model.search.CharacterSearch
import com.elacqua.albedo.data.remote.jikan_api.model.search.MangaSearch
import com.elacqua.albedo.data.remote.jikan_api.model.search.PeopleSearch
import com.elacqua.albedo.data.remote.jikan_api.service.GenreService
import com.elacqua.albedo.data.remote.jikan_api.service.ScheduleService
import com.elacqua.albedo.data.remote.jikan_api.service.SearchService
import com.elacqua.albedo.data.remote.quote_api.Quote
import com.elacqua.albedo.data.remote.quote_api.QuoteRetrofit

class RemoteRepository {
    private val genreService = JikanApiClient.instance.create(GenreService::class.java)
    private val scheduleService = JikanApiClient.instance.create(ScheduleService::class.java)
    private val searchService = JikanApiClient.instance.create(SearchService::class.java)
    private val quoteRetrofit = QuoteRetrofit.instance

    suspend fun getQuote(): Quote {
        return quoteRetrofit.getRandomQuote()
    }

    suspend fun getMultipleAnimeByGenreId(id: IntArray): ArrayList<AnimeGenre> {
        val result = ArrayList<AnimeGenre>()
        id.forEach {
            result.add(genreService.getAnimeByGenreId(it))
        }
        return result
    }

    suspend fun getAnimeByGenreId(id: Int): AnimeGenre {
        return genreService.getAnimeByGenreId(id)
    }

    suspend fun getMultipleMangaByGenreId(id: IntArray): ArrayList<MangaGenre> {
        val result = ArrayList<MangaGenre>()
        id.forEach {
            result.add(genreService.getMangaByGenreId(it))
        }
        return result
    }

    suspend fun getMangaByGenreId(id: Int): MangaGenre {
        return genreService.getMangaByGenreId(id)
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

    suspend fun getSearchResultAnime(searchType: String, query: String): AnimeSearch {
        return searchService.getSearchResultAnime(searchType, query, 1)
    }

    suspend fun getSearchResultManga(searchType: String, query: String): MangaSearch {
        return searchService.getSearchResultManga(searchType, query, 1)
    }

    suspend fun getSearchResultPeople(searchType: String, query: String): PeopleSearch {
        return searchService.getSearchResultPeople(searchType, query, 1)
    }

    suspend fun getSearchResultCharacter(searchType: String, query: String): CharacterSearch {
        return searchService.getSearchResultCharacter(searchType, query, 1)
    }
}
