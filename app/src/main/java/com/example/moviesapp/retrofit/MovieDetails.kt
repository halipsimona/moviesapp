package com.example.moviesapp.retrofit

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MovieDetails(
    @SerializedName("budget")
    val budget: Long,
    @SerializedName("homepage")
    val homepage: String,
    @SerializedName("original_title")
    val movieTitle: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("revenue")
    val revenue: Long,
    @SerializedName("genres")
    val genreIds: List<Genre>,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("backdrop_path")
    val path: String
): java.io.Serializable