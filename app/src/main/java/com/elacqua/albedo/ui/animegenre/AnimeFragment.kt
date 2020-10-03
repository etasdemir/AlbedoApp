package com.elacqua.albedo.ui.animegenre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.recyclerview.widget.LinearLayoutManager
import com.elacqua.albedo.R
import com.elacqua.albedo.data.remote.jikan_api.model.Anime
import com.elacqua.albedo.data.remote.jikan_api.model.AnimeGenre
import kotlinx.android.synthetic.main.anime_fragment.*
import timber.log.Timber

class AnimeFragment : Fragment() {

    private val viewModel: AnimeViewModel by viewModels()
    private lateinit var adapter: AnimeRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initObservers()

    }

    private fun initRecyclerView() {
        adapter = AnimeRecyclerAdapter(
            object : OnAnimeSelectedListener {
                override fun onClick(anime: Anime) {
                    navigateToAnimeDetail(anime)
                }
            },
            object : OnAnimeCategorySelectedListener {
                override fun onClick(animeGenre: AnimeGenre) {
                    navigateToAnimeCategory(animeGenre)
                }
            }

        )
        val layoutManager = LinearLayoutManager(recycler_view_anime.context)
        recycler_view_anime.layoutManager = layoutManager
        recycler_view_anime.adapter = adapter
    }

    private fun navigateToAnimeDetail(anime: Anime) {
        Timber.e("Anime clicked: $anime")
        findNavController().navigate(R.id.action_animeFragment_to_animeDetailFragment)
    }

    private fun navigateToAnimeCategory(animeGenre: AnimeGenre) {
        Timber.e("item clicked: ${animeGenre.malUrl?.name}")
//        findNavController().navigate() R.id.toCategory
    }

    private fun initObservers() {
        setQuoteObserver()
        setAnimeGenreObserver()
        setAnimeGenresObserver()
    }

    private fun setQuoteObserver() {
        viewModel.quote.observe(viewLifecycleOwner, {
            adapter.setQuoteItem(it)
        })
    }

    private fun setAnimeGenreObserver() {
        viewModel.animeGenre.observe(viewLifecycleOwner, {
            adapter.addAnimeGenre(it)
        })
    }

    private fun setAnimeGenresObserver() {
        viewModel.animeGenres.observe(viewLifecycleOwner, {
            adapter.addAllAnimeGenres(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.anime_fragment, container, false)
    }
}