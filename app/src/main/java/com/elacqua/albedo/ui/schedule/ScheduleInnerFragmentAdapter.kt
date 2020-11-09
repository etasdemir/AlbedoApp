package com.elacqua.albedo.ui.schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elacqua.albedo.R
import com.elacqua.albedo.data.remote.jikan_api.model.Anime
import kotlinx.android.synthetic.main.recycler_item.view.*

class ScheduleInnerFragmentAdapter(
    private val animeList: List<Anime>,
    private val listener: OnScheduleAnimeSelected
) : RecyclerView.Adapter<ScheduleInnerFragmentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(position)
        holder.onClick(position)
    }

    override fun getItemCount(): Int {
        return animeList.size
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(position: Int) {
            view.txt_item_name.text = animeList[position].title
            Glide.with(view)
                .load(animeList[position].imageUrl)
                .into(view.img_item_image)
        }

        fun onClick(position: Int) {
            view.setOnClickListener {
                listener.onClick(animeList[position])
            }
        }
    }
}

interface OnScheduleAnimeSelected {
    fun onClick(anime: Anime)
}