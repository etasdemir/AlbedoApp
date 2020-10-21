package com.elacqua.albedo.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elacqua.albedo.R
import com.elacqua.albedo.data.local.model.Item
import kotlinx.android.synthetic.main.fragment_recycler_item.view.*
import kotlinx.android.synthetic.main.fragment_schedule_recycler_item.view.*

private const val NUMBER_OF_ITEM = 4

class ProfileItemsRecyclerAdapter
    : RecyclerView.Adapter<ProfileItemsRecyclerAdapter.ViewHolder>(){

    private val watchedAnime = ArrayList<Item>()
    private val readManga = ArrayList<Item>()
    private val favouriteAnime = ArrayList<Item>()
    private val favouriteManga = ArrayList<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.fragment_schedule_recycler_item, parent, false)
        view.recycler_schedule_inner.run {
            layoutManager = LinearLayoutManager(parent.context, LinearLayoutManager.HORIZONTAL, false)
            setRecycledViewPool(RecyclerView.RecycledViewPool())
            setHasFixedSize(true)
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount() = NUMBER_OF_ITEM

    fun setItems(itemList: List<Item>){
        for (item in itemList){
            if (item.isFinished && item.type == "anime"){
                watchedAnime.add(item)
            } else if (item.isFinished && item.type == "manga"){
                readManga.add(item)
            }

            if (item.isFavourite && item.type == "anime"){
                favouriteAnime.add(item)
            } else if (item.isFavourite && item.type == "manga"){
                favouriteManga.add(item)
            }
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        fun onBind(position: Int) {
            when (position){
                0 -> {
                    view.recycler_schedule_inner.adapter = ProfileItemsInnerAdapter(watchedAnime)
                    view.txt_schedule_day.text = view.resources.getString(R.string.profile_watched_anime)
                }
                1 -> {
                    view.recycler_schedule_inner.adapter = ProfileItemsInnerAdapter(readManga)
                    view.txt_schedule_day.text = view.resources.getString(R.string.profile_read_manga)
                }
                2 -> {
                    view.recycler_schedule_inner.adapter = ProfileItemsInnerAdapter(favouriteAnime)
                    view.txt_schedule_day.text = view.resources.getString(R.string.profile_favourite_anime)
                }
                else -> {
                    view.recycler_schedule_inner.adapter = ProfileItemsInnerAdapter(favouriteManga)
                    view.txt_schedule_day.text = view.resources.getString(R.string.profile_favourite_manga)
                }
            }
        }

    }

}