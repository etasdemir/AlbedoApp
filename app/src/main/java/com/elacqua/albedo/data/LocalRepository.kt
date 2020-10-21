package com.elacqua.albedo.data

import com.elacqua.albedo.data.local.dao.FavouriteQuoteDao
import com.elacqua.albedo.data.local.dao.ItemDao
import com.elacqua.albedo.data.local.dao.ItemListDao
import com.elacqua.albedo.data.local.model.FavouriteQuote
import com.elacqua.albedo.data.local.model.Item
import com.elacqua.albedo.data.local.model.ItemList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor(
    private val favouriteQuoteDao: FavouriteQuoteDao,
    private val itemDao: ItemDao,
    private val itemListDao: ItemListDao
) {

    suspend fun addFavouriteQuote(favouriteQuote: FavouriteQuote) {
        favouriteQuoteDao.addQuote(favouriteQuote)
    }

    suspend fun deleteFavouriteQuote(favouriteQuote: FavouriteQuote) {
        favouriteQuoteDao.removeFromFavourite(favouriteQuote)
    }

    suspend fun getAllFavouriteQuotes(): List<FavouriteQuote> {
        return favouriteQuoteDao.getAllQuotes()
    }

    suspend fun addItem(item: Item) = itemDao.addItem(item)

    suspend fun deleteItem(item: Item) = itemDao.deleteItem(item)

    suspend fun getItemWithId(id: Int) = itemDao.getItemWithId(id)

    suspend fun getAllItems() = itemDao.getAllItems()

    suspend fun addItemToItemList (itemList: ItemList) = itemListDao.addItemToList(itemList)

    suspend fun getAllListNamesAnime() = itemListDao.getAllListNamesAnime()

    suspend fun getAllListNamesManga() = itemListDao.getAllListNamesManga()

    suspend fun getAllLists() = itemListDao.getAllLists()

    suspend fun getFinishedItemCountByType(type: String) =
        itemDao.getFinishedItemCountByType(type)

    suspend fun getWatchedEpisodesSum() = itemDao.getWatchedEpisodesSum()

    suspend fun getItemsInList(itemType: String, listName: String) = itemListDao.getItemsInList(itemType, listName)

    suspend fun deleteItemFromList (itemList: ItemList) = itemListDao.deleteItemFromList(itemList)

    suspend fun deleteList(listName: String, type: String) = itemListDao.deleteList(listName, type)
}