package com.elacqua.albedo.ui.profile

import com.elacqua.albedo.data.local.model.ItemList

interface OnSavedListSelected {
    fun deleteItemFromList(itemList: ItemList)
    fun deleteList(itemList: ItemList)
}