package com.elacqua.albedo.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.elacqua.albedo.data.local.model.Item

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)

    @Query("select * from Item where malId = :id")
    suspend fun getItemWithId(id: Int): Item

    @Query("select * from Item where type = :type and isFavourite ")
    fun getFavouriteItems(type: String): LiveData<List<Item>>

    @Query("select * from Item where type = :type and isFinished")
    fun getFinishedItems(type: String): LiveData<List<Item>>

}