package com.elacqua.albedo.di

import android.content.Context
import com.elacqua.albedo.MainActivity
import com.elacqua.albedo.data.local.DatabaseModule
import com.elacqua.albedo.data.remote.NetworkModule
import com.elacqua.albedo.ui.animedetail.AnimeDetailFragment
import com.elacqua.albedo.ui.animegenre.AnimeFragment
import com.elacqua.albedo.ui.favouritequote.FavouriteQuoteFragment
import com.elacqua.albedo.ui.genre.GenreFragment
import com.elacqua.albedo.ui.home.HomeFragment
import com.elacqua.albedo.ui.mangadetail.MangaDetailFragment
import com.elacqua.albedo.ui.mangagenre.MangaFragment
import com.elacqua.albedo.ui.profile.ProfileFragment
import com.elacqua.albedo.ui.schedule.ScheduleFragment
import com.elacqua.albedo.ui.search.SearchFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: AnimeDetailFragment)
    fun inject(fragment: AnimeFragment)
    fun inject(fragment: GenreFragment)
    fun inject(fragment: MangaDetailFragment)
    fun inject(fragment: MangaFragment)
    fun inject(fragment: ScheduleFragment)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: FavouriteQuoteFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance appContext: Context): AppComponent
    }
}