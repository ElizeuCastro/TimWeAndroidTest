package com.example.starwarsmovies.domain.di

import com.example.starwarsmovies.domain.useCase.GetMovieUseCase
import org.koin.dsl.module

object DomainModule {

    val modules = module {
        factory { GetMovieUseCase(get()) }
    }
}