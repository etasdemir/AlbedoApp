package com.elacqua.albedo.ui.animegenre

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elacqua.albedo.R
import com.elacqua.albedo.data.remote.jikan_api.model.Anime
import com.elacqua.albedo.ui.OnAnimeSelectedListener
import kotlinx.android.synthetic.main.recycler_item.view.*

class AnimeInnerRecyclerAdapter(
    private val animeList: List<Anime>,
    private val listener: OnAnimeSelectedListener
)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).bindView(position)
        holder.viewOnClick(position)
    }

    override fun getItemCount(): Int {
        return animeList.size
    }

    inner class ItemViewHolder(private val view: View)
        : RecyclerView.ViewHolder(view){

        fun bindView(position: Int) {
            view.txt_item_name.text = animeList[position].title
            Glide
                .with(view.context)
                .load(animeList[position].imageUrl)
                .into(view.img_item_image)
        }

        fun viewOnClick(position: Int){
            view.setOnClickListener {
                listener.onClick(animeList[position].malId)
            }
        }
    }
}

