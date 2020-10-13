package com.elacqua.albedo.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.elacqua.albedo.R
import com.elacqua.albedo.data.remote.jikan_api.model.Character
import kotlinx.android.synthetic.main.fragment_search_recycler_item_character.view.*

class CharacterAdapter(private val listener: OnSearchSelected<Character>) :
    SearchRecyclerAdapter<Character>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.fragment_search_recycler_item_character, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(private val view: View) : SearchViewHolder(view) {
        override fun onBind(position: Int) {
            view.txt_search_recycler_character_name.text = dataList[position].name
            view.txt_search_recycler_character_alias.text =
                dataList[position].alternativeNames.toString()

            var animeNames = ""
            for (anime in dataList[position].anime){
                animeNames += "${anime.title}, "
            }
            view.txt_search_recycler_character_anime.text = animeNames

            Glide.with(view).load(dataList[position].imageUrl)
                .into(view.img_search_recycler_character)
        }

        override fun onClick(position: Int) {
            view.setOnClickListener {
                listener.onClick(dataList[position])
            }
        }
    }
}