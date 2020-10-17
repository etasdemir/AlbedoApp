package com.elacqua.albedo.data.local.dao

import androidx.room.*
import com.elacqua.albedo.data.local.model.FavouriteQuote

@Dao
interface FavouriteQuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuote(quote: FavouriteQuote)

    @Query("select * from FavouriteQuote")
    suspend fun getAllQuotes(): List<FavouriteQuote>

    @Delete
    suspend fun removeFromFavourite(quote: FavouriteQuote)
}