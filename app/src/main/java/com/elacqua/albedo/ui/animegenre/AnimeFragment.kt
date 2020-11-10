package com.elacqua.albedo.ui.animegenre

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.elacqua.albedo.AlbedoApp
import com.elacqua.albedo.R
import com.elacqua.albedo.data.remote.jikan_api.model.AnimeGenre
import com.elacqua.albedo.data.remote.quote_api.Quote
import com.elacqua.albedo.ui.OnAnimeSelectedListener
import com.elacqua.albedo.ui.OnQuoteClickListener
import com.elacqua.albedo.util.GenreType
import kotlinx.android.synthetic.main.anime_fragment.*
import javax.inject.Inject

class AnimeFragment : Fragment() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    private val viewModel: AnimeViewModel by viewModels { vmFactory }
    private lateinit var adapter: AnimeRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshQuote()
        initRecyclerView()
        initObservers()
    }

    private fun initRecyclerView() {
        adapter = AnimeRecyclerAdapter(
            object : OnAnimeSelectedListener {
                override fun onClick(animeId: Int) {
                    navigateToAnimeDetail(animeId)
                }
            },
            object : OnAnimeCategorySelectedListener {
                override fun onClick(animeGenre: AnimeGenre) {
                    navigateToAnimeCategory(animeGenre)
                }
            },
            object : OnQuoteClickListener {
                override fun onRefreshClick() {
                    refreshQuote()
                }

                override fun onFavouriteClick(quote: Quote) {
                    viewModel.favouriteClicked(quote)
                }
            }
        )
        val layoutManager = LinearLayoutManager(requireContext())
        recycler_view_anime.layoutManager = layoutManager
        recycler_view_anime.setHasFixedSize(true)
    }

    private fun navigateToAnimeDetail(animeId: Int) {
        val bundle = bundleOf(getString(R.string.key_anime_id) to animeId)
        findNavController().navigate(R.id.action_animeFragment_to_animeDetailFragment, bundle)
    }

    private fun refreshQuote(){
        val inputStream = resources.assets.open("quote_data.json")
        viewModel.refreshQuote(inputStream)
    }

    private fun navigateToAnimeCategory(animeGenre: AnimeGenre) {
        val args =
            bundleOf(getString(R.string.key_genre_type) to GenreType.ANIME,
                getString(R.string.key_genre_id) to animeGenre.malUrl.malId)
        findNavController().navigate(R.id.action_animeFragment_to_genreFragment, args)
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as AlbedoApp).appComponent.inject(this)
    }

    override fun onStart() {
        recycler_view_anime.adapter = adapter
        super.onStart()
    }

    override fun onStop() {
        recycler_view_anime.adapter = null
        super.onStop()
    }
}