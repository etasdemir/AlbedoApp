package com.elacqua.albedo.ui.mangagenre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.albedo.data.RemoteRepository
import com.elacqua.albedo.data.local.LocalRepository
import com.elacqua.albedo.data.local.model.FavouriteQuote
import com.elacqua.albedo.data.remote.jikan_api.model.MangaGenre
import com.elacqua.albedo.data.remote.quote_api.Quote
import com.elacqua.albedo.util.MangaGenreId
import com.elacqua.albedo.util.Utility
import kotlinx.coroutines.launch
import javax.inject.Inject

class MangaViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) : ViewModel() {

    private val _quote = MutableLiveData<Quote>()
    val quote: LiveData<Quote> = _quote

    private val _mangaGenre = MutableLiveData<MangaGenre>()
    val mangaGenre: LiveData<MangaGenre> = _mangaGenre

    private val _mangaGenres = MutableLiveData<ArrayList<MangaGenre>>()
    val mangaGenres = _mangaGenres

    init {
        getQuote()
        getMangaByGenres(
            MangaGenreId.Mystery, MangaGenreId.Demons, MangaGenreId.Historical,
            MangaGenreId.Magic, MangaGenreId.Martial)
    }

    private fun getMangaByGenres(vararg id: Int) {
        viewModelScope.launch {
            val result = remoteRepository.getMultipleMangaByGenreId(id)
            _mangaGenres.postValue(result)
        }
    }

    private suspend fun getMangaById(id: Int){
        val result = remoteRepository.getMangaByGenreId(id)
        _mangaGenre.postValue(result)
    }

    private fun getQuote(){
        viewModelScope.launch {
            val result = remoteRepository.getQuote()
            if (result.data.isNullOrEmpty()){
                _quote.postValue(Quote())
            } else {
                val value = result.data[0]
                if (isQuoteFavorite(value)){
                    value.isFavourite = true
                }
                _quote.postValue(value)
            }
        }
    }

    private suspend fun isQuoteFavorite(quote: Quote): Boolean {
        val favouriteQuotes = localRepository.getAllFavouriteQuotes()
        val hash = Utility.getQuoteMd5Hash(quote)
        for (quotes in favouriteQuotes){
            if (quotes.id == hash){
                return true
            }
        }
        return false
    }

    fun refreshQuote() {
        getQuote()
    }

    fun favouriteClicked(quote: Quote) {
        val hash = Utility.getQuoteMd5Hash(quote)
        val favQuote = FavouriteQuote(hash, quote.anime, quote.character, quote.quote)
        if (quote.isFavourite){
            quote.isFavourite = false
            removeQuoteFromFavourites(favQuote)
        } else {
            quote.isFavourite = true
            addQuoteToFavourites(favQuote)
        }
        _quote.value = quote
    }

    private fun removeQuoteFromFavourites(quote: FavouriteQuote) {
        viewModelScope.launch {
            localRepository.deleteFavouriteQuote(quote)
        }
    }

    private fun addQuoteToFavourites(quote: FavouriteQuote) {
        viewModelScope.launch {
            localRepository.addFavouriteQuote(quote)
        }
    }

}