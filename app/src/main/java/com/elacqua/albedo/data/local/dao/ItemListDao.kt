package com.elacqua.albedo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.elacqua.albedo.data.local.model.Item
import com.elacqua.albedo.data.local.model.ItemList

@Dao
interface ItemListDao{

    @Insert
    suspend fun addItemToList (itemList: ItemList)

    @Delete
    suspend fun deleteItemFromList (itemList: ItemList)

    @Query ("select distinct list_name from ItemList where type = 'anime' ")
    suspend fun getAllListNamesAnime(): List<String>

    @Query("select distinct list_name from ItemList where type = 'manga' ")
    suspend fun getAllListNamesManga(): List<String>

    @Query("select * from itemlist group by list_name")
    suspend fun getAllLists(): List<ItemList>

    @Query("select Item.* from Item, ItemList where item_id = malId and ItemList.type = :itemType and list_name = :listName")
    suspend fun getItemsInList(itemType: String, listName: String): List<Item>
}