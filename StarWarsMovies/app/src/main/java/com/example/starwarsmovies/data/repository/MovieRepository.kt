package com.example.starwarsmovies.data.repository

import com.example.starwarsmovies.common.Resource
import com.example.starwarsmovies.data.StarWarsMoviesApi
import com.example.starwarsmovies.data.remote.toMovie
import com.example.starwarsmovies.domain.model.Movie
import com.example.starwarsmovies.domain.repository.MovieRepository
import retrofit2.HttpException
import java.io.IOException

class MovieRepositoryImpl(
    private val api: StarWarsMoviesApi
) : MovieRepository {

    override suspend fun getMovies(): Resource<List<Movie>> = try {
        val movies = api.getMovies().map { it.toMovie() }
        Resource.Success<List<Movie>>(movies)
    } catch (e: HttpException) {
        Resource.Error<List<Movie>>(e.localizedMessage ?: "An unexpected error occurred")
    } catch (e: IOException) {
        Resource.Error<List<Movie>>("Couldn't reach server. Check your internet connection")
    }
}