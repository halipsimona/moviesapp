package com.example.moviesapp.allmovies

import com.example.moviesapp.retrofit.MovieDetails
import com.example.moviesapp.retrofit.MovieList

data class MovieViewState(
    val addedToWatchList: Boolean = false,
    val movieList: MovieList?,
    val movieDetails: MovieDetails?
)
