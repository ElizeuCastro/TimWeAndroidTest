package com.example.starwarsmovies.respository

import com.example.starwarsmovies.common.Resource
import com.example.starwarsmovies.domain.model.Movie
import com.example.starwarsmovies.domain.repository.MovieRepository

class FakeMovieRepository(private val shouldReturnSuccess: Boolean? = true) : MovieRepository {

    private val movies = listOf(
        Movie(
            cover = "https://www.coverwhiz.com/uploads/movies/star-wars-episode-iv-a-new-hope.jpg",
            director = "George Lucas",
            episodeId = "IV",
            releaseDate = "25-05-1977",
            title = "A New Hope"
        )
    )

    override suspend fun getMovies(): Resource<List<Movie>> {
        if (shouldReturnSuccess == true) {
            return Resource.Success<List<Movie>>(movies)
        }
        return Resource.Error<List<Movie>>("An unexpected error occurred")
    }
}