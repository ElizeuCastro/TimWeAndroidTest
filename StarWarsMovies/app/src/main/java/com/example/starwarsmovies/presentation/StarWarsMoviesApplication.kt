package com.example.starwarsmovies.presentation

import android.app.Application
import com.example.starwarsmovies.data.di.DataModule
import com.example.starwarsmovies.domain.di.DomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StarWarsMoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@StarWarsMoviesApplication)
            modules(listOf(DataModule.modules, DomainModule.modules))
        }
    }
}