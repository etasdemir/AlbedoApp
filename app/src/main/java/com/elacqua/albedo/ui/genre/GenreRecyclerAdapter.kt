package com.elacqua.albedo.ui.genre

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elacqua.albedo.R
import com.elacqua.albedo.data.remote.jikan_api.model.Anime
import com.elacqua.albedo.data.remote.jikan_api.model.Manga
import com.elacqua.albedo.ui.OnAnimeSelectedListener
import com.elacqua.albedo.ui.OnMangaSelectedListener
import kotlinx.android.synthetic.main.recycler_item.view.*

class GenreRecyclerAdapter(
    private val animeListener: OnAnimeSelectedListener,
    private val mangaListener: OnMangaSelectedListener
) : RecyclerView.Adapter<GenreRecyclerAdapter.GenreViewHolder>() {

    private val animes = ArrayList<Anime>()
    private val mangas = ArrayList<Manga>()

    fun addAnimeList(animeList: List<Anime>) {
        animes.addAll(animeList)
        notifyItemInserted(animes.size)
    }

    fun addMangaList(mangaList: List<Manga>) {
        mangas.addAll(mangaList)
        notifyItemInserted(mangas.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_item, parent, false)
        return GenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.run {
            if (animes.isEmpty()) {
                bindManga(position)
                onMangaClick(position)
            } else {
                bindAnime(position)
                onAnimeClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return animes.size + mangas.size
    }

    inner class GenreViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bindAnime(position: Int) {
            view.txt_item_name.text = animes[position].title
            Glide.with(view.context).load(animes[position].imageUrl).into(view.img_item_image)
        }

        fun onAnimeClick(position: Int) {
            view.setOnClickListener {
                animeListener.onClick(animes[position].malId)
            }
        }

        fun bindManga(position: Int) {
            view.txt_item_name.text = mangas[position].title
            Glide.with(view.context).load(mangas[position].imageUrl).into(view.img_item_image)
        }

        fun onMangaClick(position: Int) {
            view.setOnClickListener {
                mangaListener.onClick(mangas[position].malId)
            }
        }
    }
}