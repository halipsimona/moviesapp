package com.example.moviesapp.retrofit

import androidx.annotation.Keep

@Keep
data class MovieList(
    val page: Int,
    val totalResults : Int,
    val totalPages: Int,
    val movieList: List<Movie>
): java.io.Serializable