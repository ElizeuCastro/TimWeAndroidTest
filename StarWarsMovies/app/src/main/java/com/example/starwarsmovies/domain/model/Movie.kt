package com.example.starwarsmovies.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val cover: String,
    val director: String,
    val episodeId: String,
    val releaseDate: String,
    val title: String
) : Parcelable