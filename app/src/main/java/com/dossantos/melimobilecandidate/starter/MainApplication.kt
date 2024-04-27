package com.dossantos.melimobilecandidate.starter

import android.app.Application
import com.dossantos.melimobilecandidate.di.getKoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application()  {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            getKoinModules()
        }
    }
}
