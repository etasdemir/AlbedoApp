package com.elacqua.albedo.data.remote.jikan_api.model

import com.google.gson.annotations.SerializedName

data class Published(
    @SerializedName("from")
    val from: String = "",
    @SerializedName("string")
    val string: String = "",
    @SerializedName("to")
    val to: String = ""
)