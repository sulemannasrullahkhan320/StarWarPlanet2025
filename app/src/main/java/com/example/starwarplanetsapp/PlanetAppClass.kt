package com.example.starwarplanetsapp

import android.app.Application
import com.example.starwarplanetsapp.Di.appModule
import com.example.starwarplanetsapp.constant.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class PlanetAppClass: Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Koin
        startKoin {
            androidContext(this@PlanetAppClass)
            modules(listOf(networkModule, appModule))
        }
    }
}