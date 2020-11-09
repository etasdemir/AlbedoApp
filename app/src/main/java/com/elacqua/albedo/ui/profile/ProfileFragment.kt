package com.elacqua.albedo.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.elacqua.albedo.AlbedoApp
import com.elacqua.albedo.R
import com.elacqua.albedo.data.local.model.ItemList
import com.elacqua.albedo.ui.OnAnimeSelectedListener
import com.elacqua.albedo.ui.OnMangaSelectedListener
import com.elacqua.albedo.ui.ViewModelFactory
import kotlinx.android.synthetic.main.profile_fragment.*
import timber.log.Timber
import javax.inject.Inject

class ProfileFragment : Fragment() {

    @Inject
    lateinit var vmFactory: ViewModelFactory
    private val viewModel: ProfileViewModel by viewModels { vmFactory }
    private lateinit var savedListAdapter: ProfileSavedListRecyclerAdapter
    private lateinit var itemsAdapter: ProfileItemsRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSavedListRecycler()
        initItemsRecycler()
        initObservers()

        txt_profile_favourite_quotes.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_favouriteQuoteFragment)
        }
    }

    private fun initSavedListRecycler() {
        setSavedListAdapter()
        val llm = LinearLayoutManager(requireContext())
        recycler_profile_lists.run {
            layoutManager = llm
            adapter = savedListAdapter
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
        }
    }

    private fun setSavedListAdapter() {
        savedListAdapter = ProfileSavedListRecyclerAdapter(
            object : OnAnimeSelectedListener {
                override fun onClick(animeId: Int) {
                    navigateToAnimeDetail(animeId)
                }
            },
            object : OnMangaSelectedListener {
                override fun onClick(mangaId: Int) {
                    navigateToMangaDetail(mangaId)
                }
            },
            object : OnSavedListSelected {
                override fun deleteItemFromList(itemList: ItemList) {
                    Timber.e("itemList: $itemList")
                    viewModel.deleteItemFromList(itemList)
                }

                override fun deleteList(itemList: ItemList) {
                    viewModel.deleteList(itemList)
                }
            }
        )
    }

    private fun initItemsRecycler() {
        setItemsAdapter()
        recycler_profile_items.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = itemsAdapter
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
        }
    }

    private fun setItemsAdapter() {
        itemsAdapter = ProfileItemsRecyclerAdapter(
            object : OnAnimeSelectedListener {
                override fun onClick(animeId: Int) {
                    navigateToAnimeDetail(animeId)
                }
            },
            object : OnMangaSelectedListener {
                override fun onClick(mangaId: Int) {
                    navigateToMangaDetail(mangaId)
                }
            }
        )
    }

    private fun navigateToAnimeDetail(animeId: Int) {
        val args = bundleOf("animeId" to animeId)
        findNavController().navigate(R.id.action_navigation_profile_to_animeDetailFragment, args)
    }

    private fun navigateToMangaDetail(mangaId: Int) {
        val args = bundleOf("mangaId" to mangaId)
        findNavController().navigate(R.id.action_navigation_profile_to_mangaDetailFragment, args)
    }

    private fun initObservers() {
        viewModel.watchedAnimeCount.observe(viewLifecycleOwner, {
            txt_profile_anime_watched.text = it.toString()
        })

        viewModel.episodeSum.observe(viewLifecycleOwner, {
            txt_profile_watched_episodes.text = (it ?: 0).toString()
        })

        viewModel.readMangaCount.observe(viewLifecycleOwner, {
            txt_profile_manga_read.text = it.toString()
        })

        viewModel.itemLists.observe(viewLifecycleOwner, {
            savedListAdapter.setItemList(it)
        })

        viewModel.items.observe(viewLifecycleOwner, {
            itemsAdapter.setItems(it)
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as AlbedoApp).appComponent.inject(this)
    }
}