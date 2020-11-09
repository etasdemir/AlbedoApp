package com.elacqua.albedo.data

import com.elacqua.albedo.data.remote.jikan_api.model.*
import com.elacqua.albedo.data.remote.jikan_api.service.*
import com.elacqua.albedo.data.remote.quote_api.QuoteList
import com.elacqua.albedo.data.remote.quote_api.QuoteService
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepository @Inject constructor(
    private val animeService: AnimeService,
    private val mangaService: MangaService,
    private val topItemService: TopItemService,
    private val scheduleService: ScheduleService,
    private val searchService: SearchService,
    private val quoteRetrofit: QuoteService
) {

    private lateinit var schedule: Schedule
    private lateinit var mostPopularAnime: Top<Anime>
    private lateinit var topAiring: Result<Anime>
    private lateinit var topUpcoming: Result<Anime>
    private lateinit var topMovies: Result<Anime>
    private lateinit var topManga: Result<Manga>
    private lateinit var topNovels: Result<Manga>
    private val animeGenres = ArrayList<AnimeGenre>()
    private val mangaGenres = ArrayList<MangaGenre>()

    suspend fun getQuote(): QuoteList {
        return try {
            quoteRetrofit.getRandomQuote()
        } catch (e: HttpException) {
            Timber.e(e)
            QuoteList()
        }
    }

    suspend fun getMultipleAnimeByGenreId(id: IntArray): ArrayList<AnimeGenre> {
        if (animeGenres.isEmpty()) {
            id.forEach {
                animeGenres.add(getAnimeByGenreId(it))
            }
        }
        return animeGenres
    }

    suspend fun getAnimeByGenreId(id: Int): AnimeGenre {
        return animeService.getAnimeByGenreId(id)
    }

    suspend fun getAnimeById(id: Int): Anime {
        return animeService.getAnimeById(id)
    }

    suspend fun getMultipleMangaByGenreId(id: IntArray): ArrayList<MangaGenre> {
        if (mangaGenres.isEmpty()) {
            id.forEach {
                mangaGenres.add(getMangaByGenreId(it))
            }
        }
        return mangaGenres
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
        if (!::schedule.isInitialized) {
            schedule = scheduleService.getScheduleAllDays()
        }
        return schedule
    }

    suspend fun getScheduleSingleDay(day: String): List<Anime> {
        return scheduleService.getScheduleSingleDay(day)
    }

    suspend fun getMostPopularAnime(): Top<Anime> {
        if (!::mostPopularAnime.isInitialized) {
            mostPopularAnime = searchService.getMostPopularAnime()
        }
        return mostPopularAnime
    }

    suspend fun getSearchResultAnime(searchType: String, query: String): Result<Anime> {
        return searchService.getSearchResultAnime(searchType, query, 1)
    }

    suspend fun getSearchResultManga(searchType: String, query: String): Result<Manga> {
        return searchService.getSearchResultManga(searchType, query, 1)
    }

    suspend fun getSearchResultPeople(searchType: String, query: String): Result<People> {
        return searchService.getSearchResultPeople(searchType, query, 1)
    }

    suspend fun getSearchResultCharacter(searchType: String, query: String): Result<Character> {
        return searchService.getSearchResultCharacter(searchType, query, 1)
    }

    suspend fun getTopAiringAnime(): Result<Anime> {
        if (!::topAiring.isInitialized) {
            topAiring = topItemService.getTopAiringAnime()
        }
        return topAiring
    }

    suspend fun getTopUpcomingAnime(): Result<Anime> {
        if (!::topUpcoming.isInitialized) {
            topUpcoming = topItemService.getTopUpcomingAnime()
        }
        return topUpcoming
    }

    suspend fun getTopMovies(): Result<Anime> {
        if (!::topMovies.isInitialized) {
            topMovies = topItemService.getTopMovies()
        }
        return topMovies
    }

    suspend fun getTopManga(): Result<Manga> {
        if (!::topManga.isInitialized) {
            topManga = topItemService.getTopManga()
        }
        return topManga
    }

    suspend fun getTopNovels(): Result<Manga> {
        if (!::topNovels.isInitialized) {
            topNovels = topItemService.getTopNovels()
        }
        return topNovels
    }


}
