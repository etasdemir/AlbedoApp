package com.elacqua.albedo.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.elacqua.albedo.AlbedoApp
import com.elacqua.albedo.R
import com.elacqua.albedo.data.remote.jikan_api.model.*
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.android.synthetic.main.fragment_search.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject lateinit var vmFactory: ViewModelProvider.Factory
    private val searchViewModel: SearchViewModel by viewModels{ vmFactory }
    private lateinit var initialAdapter: SearchRecyclerAdapter<Anime>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initObservers()
        initSearchBar()
        initSpinner()
    }

    private fun initRecyclerView() {
        initialAdapter = AnimeAdapter(object: OnSearchSelected<Anime>{
            override fun onClick(item: Anime) {
                val args = bundleOf("animeId" to item.malId)
                findNavController()
                    .navigate(R.id.action_navigation_search_to_animeDetailFragment, args)
            }
        })

        val llm = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recycler_search.adapter = initialAdapter
        recycler_search.layoutManager = llm
    }

    private fun initObservers() {

        searchViewModel.mostPopularAnime.observe(viewLifecycleOwner, {
            initialAdapter.setDataList(it.results)
        })

        searchViewModel.searchResultAnime.observe(viewLifecycleOwner, {
            val newAdapter = AnimeAdapter(object: OnSearchSelected<Anime>{
                override fun onClick(item: Anime) {
                    val args = bundleOf("animeId" to item.malId)
                    findNavController()
                        .navigate(R.id.action_navigation_search_to_animeDetailFragment, args)
                }
            })
            initBeforeSearch(newAdapter, it)
        })

        searchViewModel.searchResultManga.observe(viewLifecycleOwner, {
            val newAdapter = MangaAdapter(object: OnSearchSelected<Manga>{
                override fun onClick(item: Manga) {
                    val args = bundleOf("mangaId" to item.malId)
                    findNavController()
                        .navigate(R.id.action_navigation_search_to_mangaDetailFragment2, args)
                }
            })
            initBeforeSearch(newAdapter, it)
        })

        searchViewModel.searchResultPeople.observe(viewLifecycleOwner, {
            Timber.v("People: $it")
            val newAdapter = PeopleAdapter(object: OnSearchSelected<People>{
                override fun onClick(item: People) {
                    searchItemSelected(item)
                }
            })
            initBeforeSearch(newAdapter, it)
        })

        searchViewModel.searchResultCharacter.observe(viewLifecycleOwner, {
            Timber.v("Character: $it")
            val newAdapter = CharacterAdapter(object: OnSearchSelected<Character>{
                override fun onClick(item: Character) {
                    searchItemSelected(item)
                }
            })
            initBeforeSearch(newAdapter, it)
        })
    }

    private fun <T>initBeforeSearch(adapter: SearchRecyclerAdapter<T>, result: Result<T>){
        txt_most_popular.visibility = View.GONE
        recycler_search.adapter = adapter
        adapter.setDataList(result.results)
    }

    private fun <T>searchItemSelected(item: T) {
        Timber.v("item: $item")
    }

    private fun initSearchBar() {
        searchBar.setOnSearchActionListener(object: MaterialSearchBar.OnSearchActionListener{
            override fun onSearchStateChanged(enabled: Boolean) {
                spinner_search.isGone = enabled
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                val searchType = (spinner_search.selectedItem as String).toLowerCase(Locale.ROOT)
                getSearchResult(searchType, text.toString())
            }

            override fun onButtonClicked(buttonCode: Int) {
                Timber.v("button clicked: $buttonCode")
            }
        })
    }

    // Note: How to reduce code duplication here
    private fun getSearchResult(searchType: String, query: String){
        searchViewModel.getSearchResultAnime(searchType, query)
    }

    private fun initSpinner() {
        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, resources.getStringArray(R.array.spinnerContents))
        spinner_search.adapter = arrayAdapter
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as AlbedoApp).appComponent.inject(this)
    }
}