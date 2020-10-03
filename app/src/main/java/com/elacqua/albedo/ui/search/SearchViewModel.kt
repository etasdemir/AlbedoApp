package com.elacqua.albedo.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.albedo.data.RemoteRepository
import com.elacqua.albedo.data.remote.jikan_api.model.search.AnimeSearch
import com.elacqua.albedo.data.remote.jikan_api.model.search.CharacterSearch
import com.elacqua.albedo.data.remote.jikan_api.model.search.MangaSearch
import com.elacqua.albedo.data.remote.jikan_api.model.search.PeopleSearch
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val remoteRepository = RemoteRepository()

    private val _searchResultAnime = MutableLiveData<AnimeSearch>()
    val searchResultAnime : LiveData<AnimeSearch> = _searchResultAnime

    private val _searchResultManga = MutableLiveData<MangaSearch>()
    val searchResultManga : LiveData<MangaSearch> = _searchResultManga

    private val _searchResultPeople = MutableLiveData<PeopleSearch>()
    val searchResultPoople : LiveData<PeopleSearch> = _searchResultPeople

    private val _searchResultCharacter = MutableLiveData<CharacterSearch>()
    val searchResultCharacter : LiveData<CharacterSearch> = _searchResultCharacter

    fun getSearchResultAnime(searchType: String, query: String) {
        viewModelScope.launch {
            when(searchType){
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