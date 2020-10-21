package com.elacqua.albedo.ui.favouritequote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.albedo.data.local.LocalRepository
import com.elacqua.albedo.data.local.model.FavouriteQuote
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteQuoteViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel() {

    private val _quotes = MutableLiveData<List<FavouriteQuote>>()
    val quotes: LiveData<List<FavouriteQuote>> = _quotes

    init {
        getAllFavouriteQuotes()
    }

    private fun getAllFavouriteQuotes() {
        viewModelScope.launch {
            _quotes.postValue(localRepository.getAllFavouriteQuotes())
        }
    }

}