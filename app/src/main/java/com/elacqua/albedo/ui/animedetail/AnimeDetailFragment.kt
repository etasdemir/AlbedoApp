package com.elacqua.albedo.ui.animedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.elacqua.albedo.R
import com.elacqua.albedo.util.Utility
import kotlinx.android.synthetic.main.anime_detail_fragment.*

class AnimeDetailFragment : Fragment() {

    private val viewModel: AnimeDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.get("animeId") as Int
        viewModel.getAnimeById(id)
        initAnimeObserver()
        
    }

    private fun initAnimeObserver() {
        viewModel.anime.observe(viewLifecycleOwner, {
            txt_animedetail_title.text = it.title
            txt_animedetail_airing_start.text = it.published.string
            txt_animedetail_members.text = it.members.toString()
            txt_animedetail_episode.text = it.episodes.toString()
            txt_animedetail_score.text = it.score.toString()
            txt_animedetail_genre.text = it.genres.toString()
            txt_animedetail_synopsis.text = it.synopsis
            Glide.with(requireContext()).load(it.imageUrl).into(img_animedetail)
            Utility.setTextViewText(txt_animedetail_producers, it.producers)
            Utility.setTextViewText(txt_animedetail_opening, it.openingThemes)
            Utility.setTextViewText(txt_animedetail_ending, it.endingThemes)
        })
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.anime_detail_fragment, container, false)
    }

}