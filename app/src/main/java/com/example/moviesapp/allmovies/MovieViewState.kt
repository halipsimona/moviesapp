package com.example.moviesapp.allmovies

import com.example.moviesapp.retrofit.MovieDetails
import com.example.moviesapp.retrofit.MovieListInfo

data class MovieViewState(
    val movieList: MovieListInfo?,
    val movieDetails: MovieDetails?
)
