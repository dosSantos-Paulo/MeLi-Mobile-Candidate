package com.dossantos.melimobilecandidate.starter

import android.app.Application
import com.dossantos.melimobilecandidate.BuildConfig
import com.dossantos.melimobilecandidate.di.getMainModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApplication : Application()  {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
        setupTimber()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            getMainModules()
        }
    }

    private fun setupTimber() {
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}
