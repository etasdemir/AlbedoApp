package com.elacqua.albedo.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.elacqua.albedo.R
import com.elacqua.albedo.data.remote.jikan_api.model.Anime
import kotlinx.android.synthetic.main.fragment_search_recycler_item_anime.view.*

class AnimeAdapter(private val listener: OnSearchSelected<Anime>) : SearchRecyclerAdapter<Anime>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.fragment_search_recycler_item_anime, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(private val view: View) : SearchViewHolder(view) {
        override fun onBind(position: Int) {
            view.txt_search_recycler_title.text = dataList[position].title
            view.txt_search_recycle_score.text = "Score: ${dataList[position].score}"
            view.txt_search_recycler_synopsis.text = dataList[position].synopsis
            Glide.with(view).load(dataList[position].imageUrl).into(view.img_search_recycler)
        }

        override fun onClick(position: Int) {
            view.setOnClickListener {
                listener.onClick(dataList[position])
            }
        }
    }
}