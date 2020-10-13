package com.elacqua.albedo.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.elacqua.albedo.R
import com.elacqua.albedo.data.remote.jikan_api.model.Manga
import kotlinx.android.synthetic.main.fragment_search_recycler_item_manga.view.*

class MangaAdapter(private val listener: OnSearchSelected<Manga>)
    : SearchRecyclerAdapter<Manga>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.fragment_search_recycler_item_manga, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(private val view: View): SearchViewHolder(view){
        override fun onBind(position: Int){
            view.txt_search_recycler_manga_title.text = dataList[position].title
            view.txt_search_recycler_manga_score.text = "Score: ${dataList[position].score}"
            view.txt_search_recycler_manga_synopsis.text = dataList[position].synopsis
            Glide.with(view).load(dataList[position].imageUrl).into(view.img_search_recycler_manga)
        }

        override fun onClick(position: Int){
            view.setOnClickListener {
                listener.onClick(dataList[position])
            }
        }
    }
}