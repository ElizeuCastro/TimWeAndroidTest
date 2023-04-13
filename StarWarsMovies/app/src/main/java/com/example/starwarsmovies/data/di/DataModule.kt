package com.example.starwarsmovies.data.di

import com.example.starwarsmovies.data.ApiClient
import com.example.starwarsmovies.data.repository.MovieRepositoryImpl
import com.example.starwarsmovies.domain.repository.MovieRepository
import org.koin.dsl.module

object DataModule {

    val modules = module {
        single { ApiClient().getApiService() }
        factory<MovieRepository> { MovieRepositoryImpl(get()) }
    }
}