package com.elacqua.albedo.data.remote

import com.elacqua.albedo.data.remote.jikan_api.service.*
import com.elacqua.albedo.data.remote.quote_api.QuoteService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object NetworkModule {
    private const val JIKAN_BASE_URL = "https://api.jikan.moe/v3/"
    private const val QUOTE_BASE_URL = "https://animechanapi.xyz/api/"

    @Provides
    fun provideQuoteService(): QuoteService =
        Retrofit.Builder()
            .baseUrl(QUOTE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuoteService::class.java)

    @Provides
    fun provideJikanRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(JIKAN_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideAnimeService(retrofit: Retrofit): AnimeService =
        retrofit.create(AnimeService::class.java)

    @Provides
    fun provideMangaService(retrofit: Retrofit): MangaService =
        retrofit.create(MangaService::class.java)

    @Provides
    fun provideTopItemService(retrofit: Retrofit): TopItemService =
        retrofit.create(TopItemService::class.java)

    @Provides
    fun provideScheduleService(retrofit: Retrofit): ScheduleService =
        retrofit.create(ScheduleService::class.java)

    @Provides
    fun provideSearchService(retrofit: Retrofit): SearchService =
        retrofit.create(SearchService::class.java)

}