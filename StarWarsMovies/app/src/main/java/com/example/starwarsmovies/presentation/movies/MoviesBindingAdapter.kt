package com.example.starwarsmovies.presentation.movies

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.starwarsmovies.R
import com.example.starwarsmovies.domain.model.Movie

@BindingAdapter("movieImage")
fun ImageView.movieImage(movie: Movie?) {
    movie?.let { currentMovie ->
        val uri = currentMovie.cover.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(uri)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(this)
        contentDescription = context.getString(R.string.image_description, currentMovie.title)
    }
}

@BindingAdapter("movieTitle")
fun TextView.movieImage(movie: Movie?) {
    movie?.let { currentMovie ->
        text = context.getString(
            R.string.movie_item_title, currentMovie.episodeId, currentMovie.title
        )
    }
}