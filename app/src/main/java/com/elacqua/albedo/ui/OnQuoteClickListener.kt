package com.elacqua.albedo.ui

import com.elacqua.albedo.data.remote.quote_api.Quote

interface OnQuoteClickListener {
    fun onRefreshClick()
    fun onFavouriteClick(quote: Quote)
}