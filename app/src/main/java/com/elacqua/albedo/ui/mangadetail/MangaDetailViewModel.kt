package com.elacqua.albedo.ui.mangadetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.albedo.data.RemoteRepository
import com.elacqua.albedo.data.LocalRepository
import com.elacqua.albedo.data.local.model.Item
import com.elacqua.albedo.data.local.model.ItemList
import com.elacqua.albedo.data.remote.jikan_api.model.Manga
import kotlinx.coroutines.launch
import javax.inject.Inject

class MangaDetailViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) : ViewModel() {

    private val _manga = MutableLiveData<Manga>()
    val manga: LiveData<Manga> = _manga

    private val _localManga = MutableLiveData<Item>()
    val localManga: LiveData<Item> = _localManga

    private val _mangaItemListNames = MutableLiveData<List<String>>()
    val mangaItemListNames: LiveData<List<String>> = _mangaItemListNames

    init {
        getAllItemListManga()
    }

    fun getMangaById(id: Int) {
        viewModelScope.launch {
            val manga = remoteRepository.getMangaById(id)
            getLocalManga(manga)
            _manga.postValue(manga)
        }
    }

    private suspend fun getLocalManga(manga: Manga){
        var result: Item? = localRepository.getItemWithId(manga.malId)
        if (result == null){
            result = Item(malId = manga.malId, type = "manga",
                title = manga.title, imgUrl = manga.imageUrl)
            addItem(result)
        }
        _localManga.postValue(result)
    }

    fun addItem(item: Item) {
        viewModelScope.launch {
            localRepository.addItem(item)
        }
    }

    fun addItemToItemList (itemList: ItemList){
        viewModelScope.launch {
            localRepository.addItemToItemList(itemList)
        }
    }

    fun getAllItemListManga(){
        viewModelScope.launch {
            _mangaItemListNames.postValue(localRepository.getAllListNamesManga())
        }
    }

}