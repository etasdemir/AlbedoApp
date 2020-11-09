package com.elacqua.albedo

import android.app.Application
import com.elacqua.albedo.di.AppComponent
import com.elacqua.albedo.di.DaggerAppComponent
import timber.log.Timber

class AlbedoApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}