package com.elacqua.albedo.ui.mangagenre

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elacqua.albedo.R
import com.elacqua.albedo.data.remote.jikan_api.model.MangaGenre
import com.elacqua.albedo.data.remote.quote_api.Quote
import com.elacqua.albedo.ui.OnMangaSelectedListener
import com.elacqua.albedo.ui.OnQuoteClickListener
import kotlinx.android.synthetic.main.fragment_recycler_header.view.*
import kotlinx.android.synthetic.main.fragment_recycler_item.view.*

class MangaRecyclerAdapter (
    private val listener: OnMangaSelectedListener,
    private val categorySelectedListener: OnMangaCategorySelectedListener,
    private val quoteListener: OnQuoteClickListener
)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1
    private var quoteData: Quote.Data = Quote.Data()
    private var mangaGenreList: ArrayList<MangaGenre> = ArrayList()

    fun setQuoteItem(quote: Quote) {
        quote.data.let {
            if (it.isNotEmpty()) {
                quoteData = it[0]
                notifyDataSetChanged()
            }
        }
    }

    fun addAnimeGenre(mangaGenre: MangaGenre) {
        mangaGenreList.add(mangaGenre)
        notifyDataSetChanged()
    }

    fun addAllAnimeGenres(mangaGenres: ArrayList<MangaGenre>) {
        mangaGenreList.addAll(mangaGenres)
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
            holder.onClick()
        } else if (holder is VHItem) {
            holder.bindView(itemNewPosition)
            holder.viewOnClick(itemNewPosition)
        }
    }

    override fun getItemCount(): Int {
        return mangaGenreList.size + 1
    }

    inner class VHHeader(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView() {
            view.txt_quote.text = quoteData.quote
            view.txt_quote_anime.text = quoteData.anime
            view.txt_quote_character.text = quoteData.character
        }

        fun onClick() {
            view.btn_quote_refresh.setOnClickListener {
                quoteListener.onRefreshClick()
            }
        }
    }

    inner class VHItem(private val view: View) : RecyclerView.ViewHolder(view) {

        fun viewOnClick(position: Int){
            view.setOnClickListener {
                categorySelectedListener.onClick(mangaGenreList[position])
            }
        }

        fun bindView(position: Int) {
            view.txt_item_genre.text = mangaGenreList[position].malUrl.name
            view.recycler_inner.adapter =
                MangaInnerRecyclerAdapter(mangaGenreList[position].manga.subList(0, 20), listener)

        }
    }
}

interface OnMangaCategorySelectedListener {
    fun onClick (mangaGenre: MangaGenre)
}