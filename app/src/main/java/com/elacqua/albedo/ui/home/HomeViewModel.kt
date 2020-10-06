package com.elacqua.albedo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.albedo.data.RemoteRepository
import com.elacqua.albedo.data.remote.jikan_api.model.Anime
import com.elacqua.albedo.data.remote.jikan_api.model.Manga
import com.elacqua.albedo.data.remote.jikan_api.model.Result
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val remoteRepository = RemoteRepository()

    private val _airingAnime = MutableLiveData<Result<Anime>>()
    val airingAnime: LiveData<Result<Anime>> = _airingAnime

    private val _upcomingAnime = MutableLiveData<Result<Anime>>()
    val upcomingAnime: LiveData<Result<Anime>> = _upcomingAnime

    private val _topMovies = MutableLiveData<Result<Anime>>()
    val topMovies: LiveData<Result<Anime>> = _topMovies

    private val _topManga = MutableLiveData<Result<Manga>>()
    val topManga: LiveData<Result<Manga>> = _topManga

    private val _topNovels = MutableLiveData<Result<Manga>>()
    val topNovels: LiveData<Result<Manga>> = _topNovels

    init {
        viewModelScope.launch {
            _airingAnime.postValue(remoteRepository.getTopAiringAnime())
            _upcomingAnime.postValue(remoteRepository.getTopUpcomingAnime())
            _topMovies.postValue(remoteRepository.getTopMovies())
            _topManga.postValue(remoteRepository.getTopManga())
            _topNovels.postValue(remoteRepository.getTopNovels())
        }
    }

}