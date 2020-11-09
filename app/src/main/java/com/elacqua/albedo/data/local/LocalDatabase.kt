package com.elacqua.albedo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elacqua.albedo.data.local.dao.FavouriteQuoteDao
import com.elacqua.albedo.data.local.dao.ItemDao
import com.elacqua.albedo.data.local.dao.ItemListDao
import com.elacqua.albedo.data.local.model.FavouriteQuote
import com.elacqua.albedo.data.local.model.Item
import com.elacqua.albedo.data.local.model.ItemList

@Database(
    entities = [FavouriteQuote::class, Item::class, ItemList::class],
    version = 13,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun favouriteQuoteDao(): FavouriteQuoteDao
    abstract fun itemDao(): ItemDao
    abstract fun itemListDao(): ItemListDao

}