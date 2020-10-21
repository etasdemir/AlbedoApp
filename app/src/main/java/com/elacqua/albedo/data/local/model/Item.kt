package com.elacqua.albedo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item (
    @PrimaryKey val malId: Int = 0,
    val title: String,
    val imgUrl: String,
    val episodes: Int = 0,
    var isFavourite: Boolean = false,
    var isFinished: Boolean = false,
    val type: String = ""
)