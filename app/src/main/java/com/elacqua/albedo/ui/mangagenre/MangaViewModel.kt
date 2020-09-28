package com.elacqua.albedo.ui.mangagenre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.albedo.data.RemoteRepository
import com.elacqua.albedo.data.remote.jikan_api.model.MangaGenre
import com.elacqua.albedo.data.remote.quote_api.Quote
import com.elacqua.albedo.util.MangaGenreId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MangaViewModel : ViewModel() {

    private val remoteRepository = RemoteRepository()

    private val _quote = MutableLiveData<Quote>()
    val quote: LiveData<Quote> = _quote

    private val _mangaGenre = MutableLiveData<MangaGenre>()
    val mangaGenre: LiveData<MangaGenre> = _mangaGenre

    private val _mangaGenres = MutableLiveData<ArrayList<MangaGenre>>()
    val mangaGenres = _mangaGenres

    init {
        viewModelScope.launch {
            getQuote()
            getMangaByGenres(
                MangaGenreId.Mystery, MangaGenreId.Demons, MangaGenreId.Historical,
                MangaGenreId.Magic, MangaGenreId.Martial)
        }
    }

    private suspend fun getMangaByGenres(vararg id: Int) {
        val result = remoteRepository.getMultipleMangaByGenreId(id)
        _mangaGenres.postValue(result)
    }

    private suspend fun getMangaById(id: Int){
        val result = remoteRepository.getMangaByGenreId(id)
        _mangaGenre.postValue(result)
    }

    private suspend fun getQuote(){
        val result = remoteRepository.getQuote()
        _quote.postValue(result)
    }

}