package com.elacqua.albedo.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.albedo.data.local.LocalRepository
import com.elacqua.albedo.data.local.model.Item
import com.elacqua.albedo.data.local.model.ItemList
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel() {

    private val _watchedAnimeCount = MutableLiveData<Int>()
    val watchedAnimeCount: LiveData<Int> = _watchedAnimeCount

    private val _readMangaCount = MutableLiveData<Int>()
    val readMangaCount: LiveData<Int> = _readMangaCount

    private val _episodeSum = MutableLiveData<Int>()
    val episodeSum: LiveData<Int> = _episodeSum

    private val _itemLists = MutableLiveData<ArrayList<Pair<ItemList, List<Item>>>>()
    val itemLists: LiveData<ArrayList<Pair<ItemList, List<Item>>>> = _itemLists

    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> = _items

    init {
        viewModelScope.launch(IO) {
            getFinishedItems()
            getEpisodeSum()
            getAllItems()
            getItemLists()
        }
    }

    private suspend fun getFinishedItems(){
        _watchedAnimeCount.postValue(localRepository.getFinishedItemCountByType("anime"))
        _readMangaCount.postValue(localRepository.getFinishedItemCountByType("manga"))
    }

    private suspend fun getEpisodeSum(){
        _episodeSum.postValue(localRepository.getWatchedEpisodesSum())
    }

    private suspend fun getItemLists() {
        val listItemsPairList = ArrayList<Pair<ItemList, List<Item>>>()
        val lists = localRepository.getAllLists()
        for (list in lists){
            val items = localRepository.getItemsInList(list.type, list.name)
            listItemsPairList.add(Pair(list, items))
        }
        _itemLists.postValue(listItemsPairList)
    }

    private suspend fun getAllItems(){
        _items.postValue(localRepository.getAllItems())
    }
}