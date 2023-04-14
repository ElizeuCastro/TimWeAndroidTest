package com.example.starwarsmovies.presentation.movies

import com.example.starwarsmovies.domain.model.Movie

class MoviesListState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String = ""
)