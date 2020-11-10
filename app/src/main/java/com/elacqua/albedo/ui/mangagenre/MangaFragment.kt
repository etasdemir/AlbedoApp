package com.elacqua.albedo.ui.mangagenre

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
import com.elacqua.albedo.data.remote.jikan_api.model.MangaGenre
import com.elacqua.albedo.data.remote.quote_api.Quote
import com.elacqua.albedo.ui.OnMangaSelectedListener
import com.elacqua.albedo.ui.OnQuoteClickListener
import com.elacqua.albedo.util.GenreType
import kotlinx.android.synthetic.main.manga_fragment.*
import javax.inject.Inject

class MangaFragment : Fragment() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    private val viewModel: MangaViewModel by viewModels { vmFactory }
    private lateinit var adapter: MangaRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initViewModelObservers()
    }

    private fun initRecyclerView() {
        adapter = MangaRecyclerAdapter(
            object : OnMangaSelectedListener {
                override fun onClick(mangaId: Int) {
                    navigateToMangaDetail(mangaId)
                }
            },
            object : OnMangaCategorySelectedListener {
                override fun onClick(mangaGenre: MangaGenre) {
                    navigateToMangaCategory(mangaGenre)
                }
            },
            object : OnQuoteClickListener {
                override fun onRefreshClick() {
                    viewModel.refreshQuote()
                }

                override fun onFavouriteClick(quote: Quote) {
                    viewModel.favouriteClicked(quote)
                }
            }
        )
        val llm =
            LinearLayoutManager(requireContext())
        recycler_view_manga.layoutManager = llm
        recycler_view_manga.setHasFixedSize(true)
    }

    private fun navigateToMangaDetail(mangaId: Int) {
        val args = bundleOf(getString(R.string.key_manga_id) to mangaId)
        findNavController().navigate(R.id.action_mangaFragment_to_mangaDetailFragment, args)
    }

    private fun navigateToMangaCategory(mangaGenre: MangaGenre) {
        val args = bundleOf(getString(R.string.key_genre_type) to GenreType.MANGA,
            getString(R.string.key_genre_id) to mangaGenre.malUrl.malId)
        findNavController().navigate(R.id.action_mangaFragment_to_genreFragment, args)
    }

    private fun initViewModelObservers() {
        setQuoteObserver()
        setMangaGenreObserver()
        setMangaGenresObserver()
    }

    private fun setQuoteObserver() {
        viewModel.quote.observe(viewLifecycleOwner, {
            adapter.setQuoteItem(it)
        })
    }

    private fun setMangaGenreObserver() {
        viewModel.mangaGenre.observe(viewLifecycleOwner, {
            adapter.addMangaGenre(it)
        })
    }

    private fun setMangaGenresObserver() {
        viewModel.mangaGenres.observe(viewLifecycleOwner, {
            adapter.addAllMangaGenres(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.manga_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as AlbedoApp).appComponent.inject(this)
    }

    override fun onStart() {
        recycler_view_manga.adapter = adapter
        super.onStart()
    }

    override fun onPause() {
        recycler_view_manga.adapter = null
        super.onPause()
    }
}