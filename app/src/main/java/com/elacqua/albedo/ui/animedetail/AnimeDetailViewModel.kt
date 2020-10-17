package com.elacqua.albedo.ui.animedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.albedo.data.RemoteRepository
import com.elacqua.albedo.data.local.LocalRepository
import com.elacqua.albedo.data.local.model.Item
import com.elacqua.albedo.data.local.model.ItemList
import com.elacqua.albedo.data.remote.jikan_api.model.Anime
import kotlinx.coroutines.launch
import javax.inject.Inject

class AnimeDetailViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
): ViewModel() {

    private val _animeLocal = MutableLiveData<Item>()
    val animeLocal: LiveData<Item> = _animeLocal

    private val _anime = MutableLiveData<Anime>()
    val anime: LiveData<Anime> = _anime

    private val _itemListNames = MutableLiveData<List<String>>()
    val itemListNames: LiveData<List<String>> = _itemListNames

    init {
        getAllItemListAnime()
    }

    fun getAnimeById(id: Int) {
        viewModelScope.launch {
            getLocalAnime(id)
            _anime.postValue(remoteRepository.getAnimeById(id))
            
        }
    }

    private suspend fun getLocalAnime(id: Int) {
        var result: Item? = localRepository.getItemWithId(id)
        if (result == null){
            result = Item(malId = id, type = "anime")
            addItem(result)
        }
        _animeLocal.postValue(result)
    }

    fun addItem(item: Item){
        viewModelScope.launch {
            localRepository.addItem(item)
        }
    }

    fun addItemToItemList (itemList: ItemList){
        viewModelScope.launch {
            localRepository.addItemToItemList(itemList)
        }
    }

    fun getAllItemListAnime(){
        viewModelScope.launch {
            _itemListNames.postValue(localRepository.getAllListNamesAnime())
        }
    }


}
