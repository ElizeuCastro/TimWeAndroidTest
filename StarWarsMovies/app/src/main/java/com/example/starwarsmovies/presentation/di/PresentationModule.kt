package com.example.starwarsmovies.presentation.di

import androidx.lifecycle.SavedStateHandle
import com.example.starwarsmovies.presentation.movies.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object PresentationModule {

    val modules = module {
        viewModel { (savedStateHandle: SavedStateHandle) ->
            MoviesViewModel(
                get(),
                savedStateHandle
            )
        }
    }
}