package com.elacqua.albedo.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.elacqua.albedo.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.elacqua.albedo.data.remote.jikan_api.model.Anime
import com.elacqua.albedo.data.remote.jikan_api.model.Manga
import com.elacqua.albedo.ui.OnAnimeSelectedListener
import com.elacqua.albedo.ui.OnMangaSelectedListener
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var adapter: HomeRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val llm = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recycler_home.layoutManager = llm
        adapter = HomeRecyclerAdapter(
        object: OnAnimeSelectedListener {
            override fun onClick(anime: Anime) {
                val args = bundleOf("animeId" to anime.malId)
                findNavController()
                    .navigate(R.id.action_navigation_home_to_animeDetailFragment, args)
            }
        },
        object: OnMangaSelectedListener {
            override fun onClick(manga: Manga) {
                val args = bundleOf("mangaId" to manga.malId)
                findNavController()
                    .navigate(R.id.action_navigation_home_to_mangaDetailFragment, args)
            }
        },
        object: OnGenreSelected{
            override fun onGenreClick(type: Int) {
                val args = bundleOf("genreType" to type)
                findNavController()
                    .navigate(R.id.action_navigation_home_to_genreFragment, args)
            }
        })
        recycler_home.adapter = adapter
    }

    private fun initObservers() {
        homeViewModel.airingAnime.observe(viewLifecycleOwner, {
            adapter.setAirings(it.results)
        })

        homeViewModel.upcomingAnime.observe(viewLifecycleOwner, {
            adapter.setUpcomings(it.results)
        })

        homeViewModel.topMovies.observe(viewLifecycleOwner, {
            adapter.setMovies(it.results)
        })

        homeViewModel.topManga.observe(viewLifecycleOwner, {
            adapter.setManga(it.results)
        })

        homeViewModel.topNovels.observe(viewLifecycleOwner, {
            adapter.setNovels(it.results)
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fragment_home_menu_anime -> {
                findNavController().navigate(R.id.action_navigation_home_to_animeFragment)
            }
            R.id.fragment_home_menu_manga -> {
                findNavController().navigate(R.id.action_navigation_home_to_mangaFragment)
            }
        }
        return false
    }
}