package com.elacqua.albedo.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elacqua.albedo.R
import com.elacqua.albedo.data.local.model.Item
import com.elacqua.albedo.data.local.model.ItemList
import com.elacqua.albedo.ui.OnAnimeSelectedListener
import com.elacqua.albedo.ui.OnMangaSelectedListener
import kotlinx.android.synthetic.main.fragment_profile_saved_list_item.view.*

class ProfileSavedItemsInnerAdapter(
    itemListPair: Pair<ItemList, List<Item>>,
    private val animeListener: OnAnimeSelectedListener,
    private val mangaListener: OnMangaSelectedListener,
    private val savedListListener: OnSavedListSelected
) : RecyclerView.Adapter<ProfileSavedItemsInnerAdapter.SavedInnerViewHolder>() {

    private val items = ArrayList(itemListPair.second)
    private val itemList = itemListPair.first

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedInnerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            inflater.inflate(R.layout.fragment_profile_saved_list_item, parent, false)
        return SavedInnerViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedInnerViewHolder, position: Int) {
        holder.run {
            onBind(position)
            onClick(position)
            onDeleteClickListener(position)
        }
    }

    override fun getItemCount() = items.size

    inner class SavedInnerViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun onBind(position: Int) {
            view.txt_profile_item_name.text = items[position].title
            Glide.with(view.context)
                .load(items[position].imgUrl)
                .into(view.img_profile_item_image)
        }

        fun onClick(position: Int) {
            view.setOnClickListener {
                if (items[position].type == "anime") {
                    animeListener.onClick(items[position].malId)
                } else {
                    mangaListener.onClick(items[position].malId)
                }
            }
        }

        fun onDeleteClickListener(position: Int) {
            view.btn_profile_item_delete.setOnClickListener {
                val itemList = ItemList(items[position].malId, itemList.name, items[position].type)
                savedListListener.deleteItemFromList(itemList)
                items.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }
}