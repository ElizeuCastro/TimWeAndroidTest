package com.example.starwarsmovies.domain.repository

import com.example.starwarsmovies.data.remote.MoviesResponse

interface MovieRepository {

    suspend fun getMovies(): List<MoviesResponse>
}