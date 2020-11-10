package com.elacqua.albedo.ui.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.albedo.data.remote.RemoteRepository
import com.elacqua.albedo.data.remote.jikan_api.model.*
import com.elacqua.albedo.util.GenreType.MANGA
import com.elacqua.albedo.util.GenreType.TOP_AIRING
import com.elacqua.albedo.util.GenreType.TOP_MANGA
import com.elacqua.albedo.util.GenreType.TOP_MOVIES
import com.elacqua.albedo.util.GenreType.TOP_NOVELS
import com.elacqua.albedo.util.GenreType.TOP_UPCOMING
import kotlinx.coroutines.launch
import javax.inject.Inject

class GenreViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private val _animeItems = MutableLiveData<AnimeGenre>()
    val animeItems: LiveData<AnimeGenre> = _animeItems

    private val _mangaItems = MutableLiveData<MangaGenre>()
    val mangaItems: LiveData<MangaGenre> = _mangaItems

    fun getItemsInGenre(type: Int, genreId: Int) {
        viewModelScope.launch {
            when (type) {
                TOP_MANGA, TOP_NOVELS, MANGA -> setManga(type, genreId)
                else -> setAnime(type, genreId)
            }
        }
    }

    private suspend fun setAnime(type: Int, genreId: Int) {
        _animeItems.postValue(
            when (type) {
                TOP_AIRING -> getAnimeGenre(
                    "Top Airing Anime",
                    remoteRepository.getTopAiringAnime()
                )
                TOP_UPCOMING -> getAnimeGenre(
                    "Top Upcoming Anime",
                    remoteRepository.getTopUpcomingAnime()
                )
                TOP_MOVIES -> getAnimeGenre("Top Anime Movies", remoteRepository.getTopMovies())
                else -> remoteRepository.getAnimeByGenreId(genreId)
            }
        )
    }

    private suspend fun setManga(type: Int, genreId: Int) {
        _mangaItems.postValue(
            when (type) {
                TOP_MANGA -> {
                    getMangaGenre("Top Manga", remoteRepository.getTopManga())
                }
                TOP_NOVELS -> {
                    getMangaGenre("Top Novels", remoteRepository.getTopManga())
                }
                else -> remoteRepository.getMangaByGenreId(genreId)
            }
        )
    }

    private fun getAnimeGenre(name: String, result: Result<Anime>) =
        AnimeGenre(anime = result.results, malUrl = MalUrl(name = name))

    private fun getMangaGenre(name: String, result: Result<Manga>) =
        MangaGenre(manga = result.results, malUrl = MalUrl(name = name))

}