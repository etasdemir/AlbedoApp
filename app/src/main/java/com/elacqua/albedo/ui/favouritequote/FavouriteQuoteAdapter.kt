package com.elacqua.albedo.ui.favouritequote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elacqua.albedo.R
import com.elacqua.albedo.data.local.model.FavouriteQuote
import kotlinx.android.synthetic.main.fragment_favourite_quote_item.view.*

class FavouriteQuoteAdapter : RecyclerView.Adapter<FavouriteQuoteAdapter.ViewHolder>(){
    private val quotes = ArrayList<FavouriteQuote>()

    fun setQuotes(quoteList : List<FavouriteQuote>){
        quotes.clear()
        quotes.addAll(quoteList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_favourite_quote_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount() = quotes.size

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        fun onBind(position: Int) {
            view.run {
                txt_favourite_quote_quote.text = quotes[position].quote
                txt_favourite_quote_character.text = quotes[position].character
                txt_favourite_quote_anime.text = quotes[position].anime
            }
        }
    }
}