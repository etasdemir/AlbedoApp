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
import com.elacqua.albedo.data.remote.jikan_api.model.Manga
import com.elacqua.albedo.data.remote.jikan_api.model.MangaGenre
import com.elacqua.albedo.ui.OnMangaSelectedListener
import com.elacqua.albedo.ui.OnQuoteClickListener
import com.elacqua.albedo.util.GenreType
import kotlinx.android.synthetic.main.manga_fragment.*
import javax.inject.Inject

class MangaFragment : Fragment() {

    @Inject lateinit var vmFactory: ViewModelProvider.Factory
    private val viewModel: MangaViewModel by viewModels{ vmFactory }
    private lateinit var adapter: MangaRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initViewModelObservers()
    }

    private fun initRecyclerView() {
        adapter = MangaRecyclerAdapter(
            object: OnMangaSelectedListener {
                override fun onClick(manga: Manga) {
                    navigateToMangaDetail(manga)
                }
            },
            object: OnMangaCategorySelectedListener{
                override fun onClick(mangaGenre: MangaGenre) {
                    navigateToMangaCategory(mangaGenre)
                }
            },
            object : OnQuoteClickListener{
                override fun onRefreshClick() {
                    viewModel.refreshQuote()
                }
            }
        )
        val llm =
            LinearLayoutManager(recycler_view_manga.context, LinearLayoutManager.VERTICAL, false)
        recycler_view_manga.adapter = adapter
        recycler_view_manga.layoutManager = llm
    }

    private fun navigateToMangaDetail(manga: Manga) {
        val args = bundleOf("mangaId" to manga.malId)
        findNavController().navigate(R.id.action_mangaFragment_to_mangaDetailFragment, args)
    }

    private fun navigateToMangaCategory(mangaGenre: MangaGenre) {
        val args = bundleOf("genreType" to GenreType.MANGA, "genreId" to mangaGenre.malUrl.malId)
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
            adapter.addAnimeGenre(it)
        })
    }

    private fun setMangaGenresObserver() {
        viewModel.mangaGenres.observe(viewLifecycleOwner, {
            adapter.addAllAnimeGenres(it)
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
}