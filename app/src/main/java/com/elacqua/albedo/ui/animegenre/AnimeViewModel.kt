package com.elacqua.albedo.ui.animegenre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.albedo.data.RemoteRepository
import com.elacqua.albedo.data.remote.jikan_api.model.AnimeGenre
import com.elacqua.albedo.data.remote.quote_api.Quote
import com.elacqua.albedo.util.AnimeGenreId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimeViewModel : ViewModel() {
    private val remoteRepository = RemoteRepository()
    private val emptyQuote = Quote()

    private val _quote = MutableLiveData<Quote>()
    val quote: LiveData<Quote> = _quote

    private val _animeGenre = MutableLiveData<AnimeGenre>()
    val animeGenre: LiveData<AnimeGenre> = _animeGenre

    private val _animeGenres = MutableLiveData<ArrayList<AnimeGenre>>()
    val animeGenres = _animeGenres

    init {
        viewModelScope.launch {
            getQuote()
            getAnimeByGenres(AnimeGenreId.Action, AnimeGenreId.Adventure, AnimeGenreId.Drama,
                    AnimeGenreId.Fantasy, AnimeGenreId.SciFi)
        }
    }

    private suspend fun getAnimeByGenres(vararg id: Int) {
        val result = remoteRepository.getMultipleAnimeByGenreId(id)
        _animeGenres.postValue(result)
    }

    private suspend fun getAnimeById(id: Int){
        val result = remoteRepository.getAnimeByGenreId(id)
        _animeGenre.postValue(result)
    }

    private suspend fun getQuote(){
        val result = remoteRepository.getQuote()
        val value = result ?: emptyQuote
        _quote.postValue(value)
    }


}