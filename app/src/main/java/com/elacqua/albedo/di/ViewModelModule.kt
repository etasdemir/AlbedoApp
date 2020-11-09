package com.elacqua.albedo.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elacqua.albedo.ui.ViewModelFactory
import com.elacqua.albedo.ui.animedetail.AnimeDetailViewModel
import com.elacqua.albedo.ui.animegenre.AnimeViewModel
import com.elacqua.albedo.ui.favouritequote.FavouriteQuoteViewModel
import com.elacqua.albedo.ui.genre.GenreViewModel
import com.elacqua.albedo.ui.home.HomeViewModel
import com.elacqua.albedo.ui.mangadetail.MangaDetailViewModel
import com.elacqua.albedo.ui.mangagenre.MangaViewModel
import com.elacqua.albedo.ui.profile.ProfileViewModel
import com.elacqua.albedo.ui.schedule.ScheduleViewModel
import com.elacqua.albedo.ui.search.SearchViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun homeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AnimeDetailViewModel::class)
    internal abstract fun animeDetailViewModel(viewModel: AnimeDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AnimeViewModel::class)
    internal abstract fun animeViewModel(viewModel: AnimeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GenreViewModel::class)
    internal abstract fun genreViewModel(viewModel: GenreViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MangaDetailViewModel::class)
    internal abstract fun mangaDetailViewModel(viewModel: MangaDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MangaViewModel::class)
    internal abstract fun mangaViewModel(viewModel: MangaViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ScheduleViewModel::class)
    internal abstract fun scheduleViewModel(viewModel: ScheduleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    internal abstract fun searchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    internal abstract fun profileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouriteQuoteViewModel::class)
    internal abstract fun favouriteQuoteViewModel(viewModel: FavouriteQuoteViewModel): ViewModel

}