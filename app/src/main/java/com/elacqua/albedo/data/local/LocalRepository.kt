package com.elacqua.albedo.data.local

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

    fun getFavouriteItems(type: String) = itemDao.getFavouriteItems(type)

    fun getFinishedItems(type: String) = itemDao.getFinishedItems(type)

    suspend fun addItemToItemList (itemList: ItemList) = itemListDao.addItemToList(itemList)

    suspend fun getAllListNamesAnime() = itemListDao.getAllListNamesAnime()

    suspend fun getAllListNamesManga() = itemListDao.getAllListNamesManga()

}