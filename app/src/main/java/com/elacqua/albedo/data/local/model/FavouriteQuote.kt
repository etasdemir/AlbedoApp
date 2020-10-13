package com.elacqua.albedo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class FavouriteQuote(
    @PrimaryKey val id: String,
    val anime: String = "",
    val character: String = "",
    val quote: String = ""
)