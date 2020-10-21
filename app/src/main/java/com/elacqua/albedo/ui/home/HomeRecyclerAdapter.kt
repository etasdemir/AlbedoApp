package com.elacqua.albedo.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elacqua.albedo.R
import com.elacqua.albedo.data.remote.jikan_api.model.Anime
import com.elacqua.albedo.data.remote.jikan_api.model.Manga
import com.elacqua.albedo.ui.OnAnimeSelectedListener
import com.elacqua.albedo.ui.OnMangaSelectedListener
import com.elacqua.albedo.ui.animegenre.AnimeInnerRecyclerAdapter
import com.elacqua.albedo.ui.mangagenre.MangaInnerRecyclerAdapter
import com.elacqua.albedo.util.GenreType
import com.elacqua.albedo.util.Utility
import kotlinx.android.synthetic.main.fragment_recycler_item.view.*

private const val ROW_COUNT = 5

class HomeRecyclerAdapter (
    private val animeListener: OnAnimeSelectedListener,
    private val mangaListener: OnMangaSelectedListener,
    private val genreSelected: OnGenreSelected
    )
    : RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>(){

    private val airings = ArrayList<Anime>()
    private val upcomings = ArrayList<Anime>()
    private val movies = ArrayList<Anime>()
    private val manga = ArrayList<Manga>()
    private val novels = ArrayList<Manga>()

    fun setAirings(airingList: List<Anime>){
        airings.addAll(airingList)
        notifyDataSetChanged()
    }

    fun setUpcomings(upcomingList: List<Anime>){
        upcomings.addAll(upcomingList)
        notifyDataSetChanged()
    }

    fun setMovies(movieList: List<Anime>){
        movies.addAll(movieList)
        notifyDataSetChanged()
    }

    fun setManga(mangaList: List<Manga>){
        manga.addAll(mangaList)
        notifyDataSetChanged()
    }

    fun setNovels(novelList: List<Manga>){
        novels.addAll(novelList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.fragment_recycler_item, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return ROW_COUNT
    }

    inner class HomeViewHolder(private val view: View): RecyclerView.ViewHolder(view){

        fun onBind(position: Int){
            view.txt_item_title.text =
                view.context.resources.getString(Utility.homeCategoryTitles[position])
            view.recycler_inner.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
            view.recycler_inner.adapter = when (position){
                0 -> {
                    onClick(GenreType.TOP_AIRING)
                    AnimeInnerRecyclerAdapter(airings, animeListener)
                }
                1 -> {
                    onClick(GenreType.TOP_UPCOMING)
                    AnimeInnerRecyclerAdapter(upcomings, animeListener)
                }
                2 -> {
                    onClick(GenreType.TOP_MOVIES)
                    AnimeInnerRecyclerAdapter(movies, animeListener)
                }
                3 -> {
                    onClick(GenreType.TOP_MANGA)
                    MangaInnerRecyclerAdapter(manga, mangaListener)
                }
                else -> {
                    onClick(GenreType.TOP_NOVELS)
                    MangaInnerRecyclerAdapter(novels, mangaListener)
                }
            }
        }

        fun onClick(type: Int){
            view.setOnClickListener {
                genreSelected.onGenreClick(type)
            }
        }
    }
}

interface OnGenreSelected{
    fun onGenreClick(type: Int)
}