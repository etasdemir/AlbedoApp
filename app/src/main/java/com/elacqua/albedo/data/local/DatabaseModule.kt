package com.elacqua.albedo.data.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides

@Module
object DatabaseModule{

    @Provides
    fun provideDatabase(appContext: Context): LocalDatabase {
        return Room
            .databaseBuilder(appContext.applicationContext,
                LocalDatabase::class.java,
                "localDb")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideFavouriteQuoteDao(db: LocalDatabase) =
        db.favouriteQuoteDao()


}