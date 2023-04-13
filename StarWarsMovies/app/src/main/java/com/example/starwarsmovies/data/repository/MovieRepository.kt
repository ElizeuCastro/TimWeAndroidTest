package com.example.starwarsmovies.data.repository

import com.example.starwarsmovies.data.StarWarsMoviesApi
import com.example.starwarsmovies.data.remote.MoviesResponse
import com.example.starwarsmovies.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val api: StarWarsMoviesApi
): MovieRepository {

    override suspend fun getMovies(): List<MoviesResponse> {
        return api.getMovies()
    }
}