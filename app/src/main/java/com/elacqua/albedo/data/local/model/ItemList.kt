package com.elacqua.albedo.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["item_id", "list_name", "type"])
data class ItemList(
    @ColumnInfo(name = "item_id")
    var itemId: Int,
    @ColumnInfo(name = "list_name")
    val name: String = "",
    @ColumnInfo(name = "type")
    val type: String = ""
)