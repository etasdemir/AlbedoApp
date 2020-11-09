package com.elacqua.albedo.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elacqua.albedo.R
import com.elacqua.albedo.data.local.model.Item
import com.elacqua.albedo.data.local.model.ItemList
import com.elacqua.albedo.ui.OnAnimeSelectedListener
import com.elacqua.albedo.ui.OnMangaSelectedListener
import kotlinx.android.synthetic.main.fragment_profile_saved_recycler.view.*
import java.util.*
import kotlin.collections.ArrayList

class ProfileSavedListRecyclerAdapter(
    private val animeListener: OnAnimeSelectedListener,
    private val mangaListener: OnMangaSelectedListener,
    private val savedListListener: OnSavedListSelected
) : RecyclerView.Adapter<ProfileSavedListRecyclerAdapter.SavedListsViewHolder>() {

    private val itemLists = ArrayList<Pair<ItemList, List<Item>>>()

    fun setItemList(itemList: ArrayList<Pair<ItemList, List<Item>>>) {
        itemLists.clear()
        itemLists.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedListsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.fragment_profile_saved_recycler, parent, false)
        view.recycler_profile.run {
            layoutManager =
                LinearLayoutManager(parent.context, LinearLayoutManager.HORIZONTAL, false)
            setRecycledViewPool(RecyclerView.RecycledViewPool())
        }
        return SavedListsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedListsViewHolder, position: Int) {
        holder.onBind(position)
        holder.onClick(position)
    }

    override fun getItemCount() = itemLists.size

    inner class SavedListsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun onBind(position: Int) {
            view.txt_profile_title.text = setRecyclerItemText(position)
            view.recycler_profile.adapter =
                ProfileSavedItemsInnerAdapter(
                    itemLists[position],
                    animeListener,
                    mangaListener,
                    savedListListener
                )
        }

        private fun setRecyclerItemText(position: Int): String {
            val type = itemLists[position].first.type.toUpperCase(Locale.ROOT)
            val name = itemLists[position].first.name
            return "$name ($type)"
        }

        fun onClick(position: Int) {
            view.btn_profile_delete_list.setOnClickListener {
                savedListListener.deleteList(itemLists[position].first)
                itemLists.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }
}