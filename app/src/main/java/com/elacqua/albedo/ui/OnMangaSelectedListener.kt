package com.elacqua.albedo.ui

import com.elacqua.albedo.data.remote.jikan_api.model.Manga

interface OnMangaSelectedListener{
    fun onClick(manga: Manga)
}