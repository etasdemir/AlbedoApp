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

    @Query("select count(*) from Item where type = :type and isFinished")
    suspend fun getFinishedItemCountByType(type: String): Int

    @Query("select sum(episodes) from Item where type = 'anime' and isFinished")
    suspend fun getWatchedEpisodesSum(): Int

    @Query("select * from Item")
    suspend fun getAllItems(): List<Item>
}