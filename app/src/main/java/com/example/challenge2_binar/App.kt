package com.example.challenge2_binar

import android.app.Application
import com.example.challenge2_binar.di.KoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@App)
            modules(
                listOf(
                    KoinModule.dataModule,
                    KoinModule.uiModule
                )
            )
        }
    }
}