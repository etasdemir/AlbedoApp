package com.elacqua.albedo.ui.animedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.albedo.data.RemoteRepository
import com.elacqua.albedo.data.remote.jikan_api.model.Anime
import kotlinx.coroutines.launch

class AnimeDetailViewModel : ViewModel() {

    private val remoteRepository = RemoteRepository()

    private val _anime = MutableLiveData<Anime>()
    val anime: LiveData<Anime> = _anime

    fun getAnimeById(id: Int) {
        viewModelScope.launch {
            _anime.postValue(remoteRepository.getAnimeById(id))
        }
    }



}
