package com.elacqua.albedo.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.elacqua.albedo.ui.animegenre.AnimeFragment
import com.elacqua.albedo.ui.mangagenre.MangaFragment

private const val NUM_OF_TABS = 2

class PagerAdapter(fm: FragmentManager)
    : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return NUM_OF_TABS
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> AnimeFragment()
            else -> MangaFragment()
        }
    }
}