package com.elacqua.albedo.ui.animegenre

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elacqua.albedo.R
import com.elacqua.albedo.data.remote.jikan_api.model.AnimeGenre
import com.elacqua.albedo.data.remote.quote_api.Quote
import kotlinx.android.synthetic.main.fragment_recycler_header.view.*
import kotlinx.android.synthetic.main.fragment_recycler_item.view.*

class AnimeRecyclerAdapter (
    private val listener: OnAnimeSelectedListener,
    private val categorySelectedListener: OnAnimeCategorySelectedListener
)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1
    private var quoteData: Quote.Data = Quote.Data()
    private var animeGenreList: ArrayList<AnimeGenre> = ArrayList()

    fun setQuoteItem(quote: Quote) {
        quote.data.let {
            if (it.isNotEmpty()) {
                quoteData = it[0]
                notifyDataSetChanged()
            }
        }
    }

    fun addAnimeGenre(animeGenre: AnimeGenre) {
        animeGenreList.add(animeGenre)
        notifyDataSetChanged()
    }

    fun addAllAnimeGenres(animeGenres: ArrayList<AnimeGenre>) {
        animeGenreList.addAll(animeGenres)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_HEADER) {
            val view = inflater
                .inflate(R.layout.fragment_recycler_header, parent, false)
            VHHeader(view)
        } else {
            val view = inflater
                .inflate(R.layout.fragment_recycler_item, parent, false)
            view.recycler_inner.apply {
                layoutManager =
                    LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
                setRecycledViewPool(RecyclerView.RecycledViewPool())
            }
            VHItem(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_HEADER
        } else {
            TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemNewPosition = position - 1
        if (holder is VHHeader) {
            holder.bindView()
        } else if (holder is VHItem) {
            holder.bindView(itemNewPosition)
            holder.viewOnClick(itemNewPosition)
        }
    }

    override fun getItemCount(): Int {
        return animeGenreList.size + 1
    }

    inner class VHHeader(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView() {
            view.txt_quote.text = quoteData.quote
            view.txt_quote_anime.text = quoteData.anime
            view.txt_quote_character.text = quoteData.character
        }
    }

    inner class VHItem(private val view: View) : RecyclerView.ViewHolder(view) {

        fun viewOnClick(position: Int){
            view.setOnClickListener {
                categorySelectedListener.onClick(animeGenreList[position])
            }
        }

        fun bindView(position: Int) {
            view.txt_item_genre.text = animeGenreList[position].malUrl?.name
            view.recycler_inner.adapter =
                AnimeInnerRecyclerAdapter(animeGenreList[position].anime.subList(0, 20), listener)

        }
    }
}

interface OnAnimeCategorySelectedListener{
    fun onClick(animeGenre: AnimeGenre)
}