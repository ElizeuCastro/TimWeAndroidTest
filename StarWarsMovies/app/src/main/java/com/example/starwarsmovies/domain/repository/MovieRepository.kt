package com.example.starwarsmovies.domain.repository

import com.example.starwarsmovies.common.Resource
import com.example.starwarsmovies.domain.model.Movie

interface MovieRepository {

    suspend fun getMovies(): Resource<List<Movie>>
}