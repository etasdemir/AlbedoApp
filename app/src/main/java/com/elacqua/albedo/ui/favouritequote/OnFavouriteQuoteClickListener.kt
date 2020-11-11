package com.elacqua.albedo.ui.favouritequote

import com.elacqua.albedo.data.local.model.FavouriteQuote

interface OnFavouriteQuoteClickListener {
    fun onClick(quote: FavouriteQuote)
}