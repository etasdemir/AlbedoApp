package com.elacqua.albedo.ui.mangagenre

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elacqua.albedo.R
import com.elacqua.albedo.data.remote.jikan_api.model.Manga
import com.elacqua.albedo.ui.OnMangaSelectedListener
import kotlinx.android.synthetic.main.recycler_item.view.*

class MangaInnerRecyclerAdapter(
    private val mangaList: List<Manga>,
    private val listener: OnMangaSelectedListener
)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            inflater.inflate(R.layout.recycler_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).bindView(position)
        holder.viewOnClick(position)
    }

    override fun getItemCount(): Int {
        return mangaList.size
    }

    inner class ItemViewHolder(private val view: View)
        : RecyclerView.ViewHolder(view){

        fun bindView(position: Int) {
            view.txt_item_name.text = mangaList[position].title
            Glide
                .with(view.context)
                .load(mangaList[position].imageUrl)
                .into(view.img_item_image)
        }

        fun viewOnClick(position: Int){
            view.setOnClickListener {
                listener.onClick(mangaList[position].malId)
            }
        }
    }
}

