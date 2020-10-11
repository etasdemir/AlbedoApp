package com.elacqua.albedo.ui.mangadetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.elacqua.albedo.AlbedoApp
import com.elacqua.albedo.R
import com.elacqua.albedo.util.Utility
import kotlinx.android.synthetic.main.manga_detail_fragment.*
import javax.inject.Inject

class MangaDetailFragment : Fragment() {

    @Inject lateinit var vmFactory: ViewModelProvider.Factory
    private val viewModel: MangaDetailViewModel by viewModels{ vmFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.get("mangaId") as Int
        viewModel.getMangaById(id)

        initMangaObserver()
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.manga_detail_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as AlbedoApp).appComponent.inject(this)
    }
}