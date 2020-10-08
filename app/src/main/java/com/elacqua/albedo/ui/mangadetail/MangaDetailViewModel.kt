package com.elacqua.albedo.ui.mangadetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.albedo.data.RemoteRepository
import com.elacqua.albedo.data.remote.jikan_api.model.Manga
import kotlinx.coroutines.launch

class MangaDetailViewModel : ViewModel() {

    private val remoteRepository = RemoteRepository()

    private val _manga = MutableLiveData<Manga>()
    val manga: LiveData<Manga> = _manga

    fun getMangaById(id: Int) {
        viewModelScope.launch {
            _manga.postValue(remoteRepository.getMangaById(id))
        }
    }


}