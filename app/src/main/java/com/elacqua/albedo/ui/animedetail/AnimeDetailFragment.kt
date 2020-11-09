package com.elacqua.albedo.ui.animedetail

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.elacqua.albedo.AlbedoApp
import com.elacqua.albedo.R
import com.elacqua.albedo.data.local.model.Item
import com.elacqua.albedo.data.local.model.ItemList
import com.elacqua.albedo.data.remote.jikan_api.model.Anime
import com.elacqua.albedo.ui.DialogItemClickListener
import com.elacqua.albedo.ui.DialogItemListAdapter
import com.elacqua.albedo.util.Utility
import kotlinx.android.synthetic.main.anime_detail_fragment.*
import kotlinx.android.synthetic.main.dialog_add_item_list.*
import javax.inject.Inject

class AnimeDetailFragment : Fragment() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    private val viewModel: AnimeDetailViewModel by viewModels { vmFactory }
    private lateinit var item: Item
    private lateinit var anime: Anime
    private lateinit var dialog: Dialog
    private lateinit var dialogAdapter: DialogItemListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog = Dialog(requireContext())
        getAnimeWithArguments()
        initObservers()
        initDialogAdapter()

        btn_animedetail_favourite.setOnClickListener {
            favouriteButtonClicked()
        }

        btn_animedetail_watched.setOnClickListener {
            watchedButtonClicked()
        }

        btn_animedetail_list_add.setOnClickListener {
            createAddItemDialog()
        }

        btn_animedetail_trailer.setOnClickListener {
            if (anime.trailerUrl != "") {
                startTrailerIntent()
            } else {
                Toast.makeText(requireContext(), "Sorry :( Trailer could not be found.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun getAnimeWithArguments() {
        val id = arguments?.get("animeId") as Int
        viewModel.getAnimeById(id)
    }

    private fun initObservers() {
        initAnimeObserver()
        initLocalAnimeObserver()
        initItemListObserver()
    }

    private fun initAnimeObserver() {
        viewModel.anime.observe(viewLifecycleOwner, { it ->
            this.anime = it
            setViews()
        })
    }

    private fun setViews() {
        txt_animedetail_title.text = anime.title
        txt_animedetail_airing_start.text = anime.published.string
        txt_animedetail_members.text = anime.members.toString()
        txt_animedetail_episode.text = anime.episodes.toString()
        txt_animedetail_score.text = anime.score.toString()
        txt_animedetail_genre.text = anime.genres.toString()
        txt_animedetail_synopsis.text = anime.synopsis
        Glide.with(requireContext()).load(anime.imageUrl).into(img_animedetail)
        Utility.setTextViewText(txt_animedetail_producers, anime.producers)
        Utility.setTextViewText(txt_animedetail_opening, anime.openingThemes)
        Utility.setTextViewText(txt_animedetail_ending, anime.endingThemes)
    }

    private fun initLocalAnimeObserver() {
        viewModel.animeLocal.observe(viewLifecycleOwner, {
            item = it
            if (it.isFavourite) {
                btn_animedetail_favourite.setImageResource(R.drawable.ic_quote_favorite_36)
            }
            if (it.isFinished) {
                btn_animedetail_watched.setImageResource(R.drawable.ic_true_36)
            }
        })
    }

    private fun initItemListObserver() {
        viewModel.itemListNames.observe(viewLifecycleOwner, {
            dialogAdapter.setItems(it)
        })
    }

    private fun initDialogAdapter() {
        dialogAdapter = DialogItemListAdapter(object : DialogItemClickListener {
            override fun onClick(itemListName: String) {
                val itemList = ItemList(item.malId, itemListName, "anime")
                viewModel.addItemToItemList(itemList)
                dialog.dismiss()
            }
        })
    }

    private fun favouriteButtonClicked() {
        item.isFavourite = !item.isFavourite
        if (item.isFavourite) {
            btn_animedetail_favourite.setImageResource(R.drawable.ic_quote_favorite_36)
        } else {
            btn_animedetail_favourite.setImageResource(R.drawable.ic_quote_unfavourite_36)
        }
        viewModel.addItem(item)
    }

    private fun watchedButtonClicked() {
        item.isFinished = !item.isFinished
        if (item.isFinished) {
            btn_animedetail_watched.setImageResource(R.drawable.ic_true_36)
        } else {
            btn_animedetail_watched.setImageResource(R.drawable.ic_false_36)
        }
        viewModel.addItem(item)
    }

    private fun createAddItemDialog() {
        dialog.run {
            setContentView(R.layout.dialog_add_item_list)
            recycler_dialog_add.layoutManager = LinearLayoutManager(requireContext())
            recycler_dialog_add.setHasFixedSize(true)
            recycler_dialog_add.adapter = dialogAdapter
            btn_dialog_add_cancel.setOnClickListener {
                dismiss()
            }
            btn_dialog_add_save.setOnClickListener {
                val listName = txt_dialog_add_list_name.text.toString()
                createNewListAndAddItem(listName)
                dismiss()
            }
            show()

        }
    }

    private fun createNewListAndAddItem(listName: String) {
        val itemId = item.malId
        val type = "anime"
        val itemList = ItemList(itemId = itemId, name = listName, type = type)
        viewModel.addItemToItemList(itemList)
    }

    private fun startTrailerIntent(){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(anime.trailerUrl))
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        return inflater.inflate(R.layout.anime_detail_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as AlbedoApp).appComponent.inject(this)
    }
}