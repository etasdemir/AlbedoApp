package com.elacqua.albedo.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elacqua.albedo.R
import com.elacqua.albedo.data.local.model.Item
import com.elacqua.albedo.data.remote.jikan_api.model.Anime
import com.elacqua.albedo.data.remote.jikan_api.model.Manga
import com.elacqua.albedo.ui.OnAnimeSelectedListener
import com.elacqua.albedo.ui.OnMangaSelectedListener
import kotlinx.android.synthetic.main.fragment_recycler_item.view.*
import kotlinx.android.synthetic.main.recycler_item.view.*

class ProfileItemsInnerAdapter (
    private val items: ArrayList<Item>,
    private val animeListener: OnAnimeSelectedListener,
    private val mangaListener: OnMangaSelectedListener
) : RecyclerView.Adapter<ProfileItemsInnerAdapter.InnerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_item, parent, false)
        return InnerViewHolder(view)
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.onBind(position)
        holder.onClick(position)
    }

    override fun getItemCount() = items.size

    inner class InnerViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        fun onBind(position: Int){
            view.txt_item_name.text = items[position].title
            Glide.with(view.context)
                .load(items[position].imgUrl)
                .into(view.img_item_image)
        }

        fun onClick(position: Int){
            view.setOnClickListener {
                if (items[position].type == "anime"){
                    animeListener.onClick(Anime(malId = items[position].malId))
                } else {
//                    mangaListener.onClick(Manga(malId = items[position].malId))
                }
            }
        }
    }
}