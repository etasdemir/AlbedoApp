package com.elacqua.albedo.ui.mangadetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.albedo.data.RemoteRepository
import com.elacqua.albedo.data.local.LocalRepository
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
            getLocalManga(id)
            _manga.postValue(remoteRepository.getMangaById(id))
        }
    }

    private suspend fun getLocalManga(id: Int){
        var result: Item? = localRepository.getItemWithId(id)
        if (result == null){
            result = Item(malId = id, type = "manga")
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