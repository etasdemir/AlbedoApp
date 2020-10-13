package com.elacqua.albedo.data.local

import com.elacqua.albedo.data.local.dao.FavouriteQuoteDao
import com.elacqua.albedo.data.local.model.FavouriteQuote
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor(
    private val favouriteQuoteDao: FavouriteQuoteDao,
){

    suspend fun addFavouriteQuote(favouriteQuote: FavouriteQuote){
        favouriteQuoteDao.addQuote(favouriteQuote)
    }

    suspend fun deleteFavouriteQuote(favouriteQuote: FavouriteQuote){
        favouriteQuoteDao.removeFromFavourite(favouriteQuote)
    }

    suspend fun getAllFavouriteQuotes(): List<FavouriteQuote> {
        return favouriteQuoteDao.getAllQuotes()
    }


}