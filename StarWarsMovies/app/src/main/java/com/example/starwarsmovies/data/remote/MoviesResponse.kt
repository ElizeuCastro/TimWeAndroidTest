package com.example.starwarsmovies.data.remote

import com.example.starwarsmovies.domain.model.Movie
import com.google.gson.annotations.SerializedName
import okhttp3.internal.immutableListOf

data class MoviesResponse(
    val cover: String,
    val director: String,
    @SerializedName("episode_id")
    val episodeId: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String
)

fun MoviesResponse.toMovie(): Movie {
    return Movie(
        cover = cover,
        director = director,
        episodeId = episodeId,
        releaseDate = releaseDate,
        title = title
    )
}