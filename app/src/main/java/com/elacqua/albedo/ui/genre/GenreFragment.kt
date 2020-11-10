package com.elacqua.albedo.ui.genre

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.elacqua.albedo.AlbedoApp
import com.elacqua.albedo.R
import com.elacqua.albedo.ui.OnAnimeSelectedListener
import com.elacqua.albedo.ui.OnMangaSelectedListener
import kotlinx.android.synthetic.main.genre_fragment.*
import javax.inject.Inject

class GenreFragment : Fragment() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    private val viewModel: GenreViewModel by viewModels { vmFactory }
    private lateinit var adapter: GenreRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getItemsUsingArguments()
        initRecyclerView()
        initObservers()
    }

    private fun getItemsUsingArguments(){
        val type = arguments?.get(getString(R.string.key_genre_type)) as Int
        val genreId = arguments?.get(getString(R.string.key_genre_id)) ?: 0
        viewModel.getItemsInGenre(type, genreId as Int)
    }

    private fun initRecyclerView() {
        adapter = GenreRecyclerAdapter(
            object : OnAnimeSelectedListener {
                override fun onClick(animeId: Int) {
                    val args = bundleOf(getString(R.string.key_anime_id) to animeId)
                    findNavController()
                        .navigate(R.id.action_genreFragment_to_animeDetailFragment, args)
                }
            },
            object : OnMangaSelectedListener {
                override fun onClick(mangaId: Int) {
                    val args = bundleOf(getString(R.string.key_manga_id) to mangaId)
                    findNavController()
                        .navigate(R.id.action_genreFragment_to_mangaDetailFragment, args)
                }
            }
        )
        val glm = GridLayoutManager(requireContext(), 2)
        recycler_genre.run {
            layoutManager = glm
            setHasFixedSize(true)
        }
    }

    private fun initObservers() {
        observeAnimeItems()
        observeMangaItems()
    }

    private fun observeAnimeItems() {
        viewModel.animeItems.observe(viewLifecycleOwner, {
            txt_genre_title.text = it.malUrl.name
            adapter.addAnimeList(it.anime)
        })
    }

    private fun observeMangaItems() {
        viewModel.mangaItems.observe(viewLifecycleOwner, {
            txt_genre_title.text = it.malUrl.name
            adapter.addMangaList(it.manga)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        return inflater.inflate(R.layout.genre_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as AlbedoApp).appComponent.inject(this)
    }

    override fun onStart() {
        recycler_genre.adapter = adapter
        super.onStart()
    }

    override fun onStop() {
        recycler_genre.adapter = null
        super.onStop()
    }
}