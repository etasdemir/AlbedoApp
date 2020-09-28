package com.elacqua.albedo.ui.mangagenre

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.elacqua.albedo.R
import com.elacqua.albedo.data.remote.jikan_api.model.MangaGenre
import kotlinx.android.synthetic.main.manga_fragment.*
import timber.log.Timber

class MangaFragment : Fragment() {

    private val viewModel: MangaViewModel by viewModels()
    private lateinit var adapter: MangaRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initViewModelObservers()
    }

    private fun initRecyclerView() {
        adapter = MangaRecyclerAdapter(
            object: OnMangaSelectedListener{
                override fun onClick(manga: MangaGenre.Manga) {
                    navigateToMangaDetail(manga)
                }
            },
            object: OnMangaCategorySelectedListener{
                override fun onClick(mangaGenre: MangaGenre) {
                    navigateToMangaCategory(mangaGenre)
                }
            }
        )
        val llm =
            LinearLayoutManager(recycler_view_manga.context, LinearLayoutManager.VERTICAL, false)
        recycler_view_manga.adapter = adapter
        recycler_view_manga.layoutManager = llm
    }

    private fun navigateToMangaDetail(manga: MangaGenre.Manga) {
        Timber.e("Manga clicked: $manga")
        findNavController().navigate(R.id.action_mangaFragment_to_mangaDetailFragment)
    }

    private fun navigateToMangaCategory(mangaGenre: MangaGenre) {
        Timber.e("item clicked: ${mangaGenre.malUrl.name}")
//        findNavController().navigate() R.id.toCategory
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
}