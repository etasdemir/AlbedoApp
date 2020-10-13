package com.elacqua.albedo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elacqua.albedo.data.local.dao.FavouriteQuoteDao
import com.elacqua.albedo.data.local.model.FavouriteQuote

@Database(entities = [FavouriteQuote::class], version = 3, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun favouriteQuoteDao(): FavouriteQuoteDao

}