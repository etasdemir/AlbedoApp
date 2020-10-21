package com.elacqua.albedo.ui.animedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.albedo.data.RemoteRepository
import com.elacqua.albedo.data.LocalRepository
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
            val anime = remoteRepository.getAnimeById(id)
            getLocalAnime(anime)
            _anime.postValue(anime)
            
        }
    }

    private suspend fun getLocalAnime(anime: Anime) {
        var result: Item? = localRepository.getItemWithId(anime.malId)
        if (result == null){
            result =
                Item(malId = anime.malId, type = "anime",
                    title = anime.title, episodes = anime.episodes, imgUrl = anime.imageUrl)
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
