package com.elacqua.albedo.ui.mangadetail

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.elacqua.albedo.ui.DialogItemClickListener
import com.elacqua.albedo.ui.DialogItemListAdapter
import com.elacqua.albedo.util.Utility
import kotlinx.android.synthetic.main.dialog_add_item_list.*
import kotlinx.android.synthetic.main.manga_detail_fragment.*
import javax.inject.Inject

class MangaDetailFragment : Fragment() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    private val viewModel: MangaDetailViewModel by viewModels { vmFactory }
    private lateinit var item: Item
    private lateinit var dialog: Dialog
    private lateinit var dialogAdapter: DialogItemListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.get(getString(R.string.key_manga_id)) as Int
        viewModel.getMangaById(id)
        dialog = Dialog(requireContext())
        initDialogAdapter()
        initObservers()
        buttonClickListeners()
    }

    private fun initDialogAdapter() {
        dialogAdapter = DialogItemListAdapter(object : DialogItemClickListener {
            override fun onClick(itemListName: String) {
                val itemList = ItemList(item.malId, itemListName, "manga")
                viewModel.addItemToItemList(itemList)
                dialog.dismiss()
            }
        })
    }

    private fun initObservers() {
        initMangaObserver()
        initLocalMangaObserver()
        initMangaItemListObserver()
    }

    private fun initMangaObserver() {
        viewModel.manga.observe(viewLifecycleOwner, {
            txt_mangadetail_title.text = it.title
            txt_mangadetail_published.text = it.published.string
            txt_mangadetail_volumes.text =
                if (it.volumes.toString() == "null") {
                    "-"
                } else {
                    it.volumes.toString()
                }
            txt_mangadetail_members.text = it.members.toString()
            txt_mangadetail_score.text = it.score.toString()
            txt_mangadetail_synopsis.text = it.synopsis
            Utility.setTextViewText(txt_mangadetail_authors, it.authors)
            Utility.setTextViewText(txt_mangadetail_genre, it.genres)
            Glide.with(requireContext()).load(it.imageUrl).into(img_mangadetail)
        })
    }

    private fun initLocalMangaObserver() {
        viewModel.localManga.observe(viewLifecycleOwner, {
            item = it
            if (item.isFavourite) {
                btn_mangadetail_favourite.setImageResource(R.drawable.ic_quote_favorite_36)
            }
            if (item.isFinished) {
                btn_mangadetail_read.setImageResource(R.drawable.ic_true_36)
            }
        })
    }

    private fun initMangaItemListObserver() {
        viewModel.mangaItemListNames.observe(viewLifecycleOwner, {
            dialogAdapter.setItems(it)
        })
    }

    private fun buttonClickListeners() {
        btn_mangadetail_favourite.setOnClickListener {
            buttonFavouriteListener()
        }

        btn_mangadetail_read.setOnClickListener {
            buttonReadListener()
        }

        btn_mangadetail_add_list.setOnClickListener {
            buttonAddListListener()
        }
    }

    private fun buttonFavouriteListener() {
        item.isFavourite = !item.isFavourite
        btn_mangadetail_favourite.setImageResource(
            if (item.isFavourite)
                R.drawable.ic_quote_favorite_36
            else
                R.drawable.ic_quote_unfavourite_36
        )
        viewModel.addItem(item)
    }

    private fun buttonReadListener() {
        item.isFinished = !item.isFinished
        btn_mangadetail_read.setImageResource(
            if (item.isFinished)
                R.drawable.ic_true_36
            else
                R.drawable.ic_false_36
        )
        viewModel.addItem(item)
    }

    private fun buttonAddListListener() {
        dialog.run {
            dialog.setContentView(R.layout.dialog_add_item_list)
            dialog.recycler_dialog_add.layoutManager = LinearLayoutManager(requireContext())
            dialog.recycler_dialog_add.adapter = dialogAdapter
            dialog.recycler_dialog_add.setHasFixedSize(true)
            btn_dialog_add_cancel.setOnClickListener { dismiss() }
            btn_dialog_add_save.setOnClickListener {
                val listName = txt_dialog_add_list_name.text.toString()
                val itemList = ItemList(item.malId, listName, "manga")
                viewModel.addItemToItemList(itemList)
                dismiss()
            }
            show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        return inflater.inflate(R.layout.manga_detail_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as AlbedoApp).appComponent.inject(this)
    }
}