package com.elacqua.albedo.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.albedo.data.RemoteRepository
import com.elacqua.albedo.data.remote.jikan_api.model.*
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val remoteRepository = RemoteRepository()

    private val _mostPopularAnime = MutableLiveData<Top<Anime>>()
    val mostPopularAnime: LiveData<Top<Anime>> = _mostPopularAnime

    private val _searchResultAnime = MutableLiveData<Search<Anime>>()
    val searchResultAnime: LiveData<Search<Anime>> = _searchResultAnime

    private val _searchResultManga = MutableLiveData<Search<Manga>>()
    val searchResultManga: LiveData<Search<Manga>> = _searchResultManga

    private val _searchResultPeople = MutableLiveData<Search<People>>()
    val searchResultPeople: LiveData<Search<People>> = _searchResultPeople

    private val _searchResultCharacter = MutableLiveData<Search<Character>>()
    val searchResultCharacter: LiveData<Search<Character>> = _searchResultCharacter

    init {
        viewModelScope.launch {
            getMostPopularAnime()
        }
    }

    private suspend fun getMostPopularAnime() {
        val result = remoteRepository.getMostPopularAnime()
        _mostPopularAnime.postValue(result)
    }

    fun getSearchResultAnime(searchType: String, query: String) {
        viewModelScope.launch {
            when (searchType) {
                "anime" -> {
                    val result = remoteRepository.getSearchResultAnime(searchType, query)
                    _searchResultAnime.postValue(result)
                }
                "manga" -> {
                    val result = remoteRepository.getSearchResultManga(searchType, query)
                    _searchResultManga.postValue(result)
                }
                "people" -> {
                    val result = remoteRepository.getSearchResultPeople(searchType, query)
                    _searchResultPeople.postValue(result)
                }
                else -> {
                    val result = remoteRepository.getSearchResultCharacter(searchType, query)
                    _searchResultCharacter.postValue(result)
                }
            }
        }
    }


}