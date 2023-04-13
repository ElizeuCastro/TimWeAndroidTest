package com.example.starwarsmovies.data

import com.example.starwarsmovies.data.remote.MoviesResponse
import retrofit2.http.GET

interface StarWarsMoviesApi {

    @GET("/dc12046175d1c54574fb")
    suspend fun getMovies(): List<MoviesResponse>

}