package com.elacqua.albedo.ui.search

import androidx.lifecycle.*
import com.elacqua.albedo.data.RemoteRepository
import com.elacqua.albedo.data.local.LocalRepository
import com.elacqua.albedo.data.remote.jikan_api.model.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) : ViewModel() {

    private val _mostPopularAnime = MutableLiveData<Top<Anime>>()
    val mostPopularAnime: LiveData<Top<Anime>> = _mostPopularAnime

    private val _searchResultAnime = MutableLiveData<Result<Anime>>()
    val searchResultAnime: LiveData<Result<Anime>> = _searchResultAnime

    private val _searchResultManga = MutableLiveData<Result<Manga>>()
    val searchResultManga: LiveData<Result<Manga>> = _searchResultManga

    private val _searchResultPeople = MutableLiveData<Result<People>>()
    val searchResultPeople: LiveData<Result<People>> = _searchResultPeople

    private val _searchResultCharacter = MutableLiveData<Result<Character>>()
    val searchResultCharacter: LiveData<Result<Character>> = _searchResultCharacter

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