package com.example.starwarsmovies.domain.useCase

import com.example.starwarsmovies.common.Resource
import com.example.starwarsmovies.domain.model.Movie
import com.example.starwarsmovies.domain.repository.MovieRepository

class GetMovieUseCase(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(): Resource<List<Movie>> = movieRepository.getMovies()

}