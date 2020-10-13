package com.elacqua.albedo.ui

import com.elacqua.albedo.data.remote.jikan_api.model.Anime

interface OnAnimeSelectedListener {
    fun onClick(anime: Anime)
}