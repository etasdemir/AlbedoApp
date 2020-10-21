package com.elacqua.albedo.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elacqua.albedo.R
import com.elacqua.albedo.data.local.model.Item
import com.elacqua.albedo.data.local.model.ItemList
import kotlinx.android.synthetic.main.dialog_recycler_item.view.*
import kotlinx.android.synthetic.main.fragment_schedule_recycler_item.view.*
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class ProfileSavedListRecyclerAdapter
    : RecyclerView.Adapter<ProfileSavedListRecyclerAdapter.SavedListsViewHolder>(){

    private val itemLists = ArrayList<Pair<ItemList, List<Item>>>()

    fun setItemList(itemList: ArrayList<Pair<ItemList, List<Item>>>){
        itemLists.clear()
        itemLists.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedListsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.fragment_schedule_recycler_item, parent, false)
        view.recycler_schedule_inner.run {
            layoutManager = LinearLayoutManager(parent.context, LinearLayoutManager.HORIZONTAL, false)
            setRecycledViewPool(RecyclerView.RecycledViewPool())
            setHasFixedSize(true)
        }
        return SavedListsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedListsViewHolder, position: Int) {
        holder.onBind(position)
        holder.onClick(position)
    }

    override fun getItemCount() = itemLists.size

    inner class SavedListsViewHolder(private val view: View)
        : RecyclerView.ViewHolder(view){

        fun onBind(position: Int) {
            view.txt_schedule_day.text = setRecyclerItemText(position)
            view.recycler_schedule_inner.adapter =
                ProfileItemsInnerAdapter(itemLists[position].second as ArrayList<Item>)
        }

        private fun setRecyclerItemText(position: Int): String {
            val type = itemLists[position].first.type.toUpperCase(Locale.ROOT)
            val name = itemLists[position].first.name
            return "$name ($type)"
        }

        fun onClick(position: Int){
            view.setOnClickListener {
                Timber.e("item clicked: ${itemLists[position]}")
            }
        }
    }
}