package com.elacqua.albedo

import android.app.Application
import android.util.Log
import com.elacqua.albedo.data.RemoteRepository
import timber.log.Timber

class AlbedoApp : Application(){

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}