package com.example.starwarsmovies.domain.useCase

import com.example.starwarsmovies.common.Resource
import com.example.starwarsmovies.data.remote.toMovie
import com.example.starwarsmovies.domain.model.Movie
import com.example.starwarsmovies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetMovieUseCase(
    private val movieRepository: MovieRepository
) {

    operator fun invoke(): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading<List<Movie>>())
            val movies = movieRepository.getMovies().map { it.toMovie() }
            emit(Resource.Success<List<Movie>>(movies))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Movie>>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Movie>>("Couldn't reach server. Check your internet connection"))
        }
    }
}