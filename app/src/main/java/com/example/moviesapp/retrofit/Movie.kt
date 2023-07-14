package com.example.moviesapp.retrofit

import androidx.annotation.Keep

@Keep
data class Movie(
    val id: Int? = 0,
    val voteAverage: Float? = 0f,
    val movieTitle: String? = "",
    val originalLanguage: String? = "",
    val originalTitle: String? = "",
    val genreIds: List<Int>? = emptyList(),
    val overview: String? = "",
    val releaseDate: String? = "",
    val path: String? = "",
    val isFromWatchlist: Boolean
): java.io.Serializable