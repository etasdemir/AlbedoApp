package com.elacqua.albedo.ui.animegenre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.albedo.data.RemoteRepository
import com.elacqua.albedo.data.local.LocalRepository
import com.elacqua.albedo.data.local.model.FavouriteQuote
import com.elacqua.albedo.data.remote.jikan_api.model.AnimeGenre
import com.elacqua.albedo.data.remote.quote_api.Quote
import com.elacqua.albedo.data.remote.quote_api.QuoteList
import com.elacqua.albedo.util.AnimeGenreId
import com.elacqua.albedo.util.Utility.getQuoteMd5Hash
import kotlinx.coroutines.launch
import javax.inject.Inject

class AnimeViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) : ViewModel() {

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

    private suspend fun getQuote(){
        val result = remoteRepository.getQuote()
        if (result.data.isNullOrEmpty()){
            _quote.postValue(Quote())
            return
        }

        if (isQuoteFavorite(result)){
            result.data[0].isFavourite = true
        }
        _quote.postValue(result.data[0])
    }

    private suspend fun isQuoteFavorite(quoteList: QuoteList): Boolean {
        val favouriteQuotes = localRepository.getAllFavouriteQuotes()
        val hash = getQuoteMd5Hash(quoteList.data[0])
        for (quotes in favouriteQuotes){
            if (quotes.id == hash){
                return true
            }
        }
        return false
    }

    fun refreshQuote() {
        viewModelScope.launch {
            getQuote()
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

    fun favouriteClicked(quote: Quote) {
        viewModelScope.launch {
            val id = getQuoteMd5Hash(quote)
            val favQuote = FavouriteQuote(id, quote.anime, quote.character, quote.quote)
            if (quote.isFavourite){
                quote.isFavourite = false
                removeQuoteFromFavourite(favQuote)
            } else {
                quote.isFavourite = true
                addQuoteToFavourite(favQuote)
            }
            _quote.postValue(quote)
        }
    }

    private suspend fun removeQuoteFromFavourite(quote: FavouriteQuote) {
        localRepository.deleteFavouriteQuote(quote)
    }

    private suspend fun addQuoteToFavourite(quote: FavouriteQuote) {
        localRepository.addFavouriteQuote(quote)
    }

}