package com.elacqua.albedo.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.elacqua.albedo.AlbedoApp
import com.elacqua.albedo.R
import com.elacqua.albedo.data.local.model.Item
import com.elacqua.albedo.data.local.model.ItemList
import com.elacqua.albedo.ui.ViewModelFactory
import kotlinx.android.synthetic.main.anime_detail_fragment.*
import kotlinx.android.synthetic.main.profile_fragment.*
import timber.log.Timber
import javax.inject.Inject

class ProfileFragment : Fragment() {

    @Inject lateinit var vmFactory: ViewModelFactory
    private val viewModel: ProfileViewModel by viewModels { vmFactory }
    private lateinit var savedListAdapter: ProfileSavedListRecyclerAdapter
    private lateinit var itemsAdapter: ProfileItemsRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSavedListRecycler()
        initItemsRecycler()
        initObservers()

        txt_profile_favourite_quotes.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_profile_to_favouriteQuoteFragment)
        }
    }

    private fun initSavedListRecycler() {
        val llm = LinearLayoutManager(requireContext())
        savedListAdapter = ProfileSavedListRecyclerAdapter()
        recycler_profile_lists.run {
            layoutManager = llm
            adapter = savedListAdapter
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
        }
    }

    private fun initItemsRecycler() {
        itemsAdapter = ProfileItemsRecyclerAdapter()
        recycler_profile_items.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = itemsAdapter
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
        }
    }

    private fun initObservers() {
        viewModel.watchedAnimeCount.observe(viewLifecycleOwner, {
            txt_profile_anime_watched.text = it.toString()
        })

        viewModel.episodeSum.observe(viewLifecycleOwner, {
            txt_profile_watched_episodes.text = it.toString()
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