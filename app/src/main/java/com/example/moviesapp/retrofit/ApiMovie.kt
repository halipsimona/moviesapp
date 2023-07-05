package com.example.moviesapp.retrofit

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ApiMovie(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("vote_average")
    val voteAverage: Float? = 0f,
    @SerializedName("title")
    val movieTitle: String? = "",
    @SerializedName("original_language")
    val originalLanguage: String? = "",
    @SerializedName("original_title")
    val originalTitle: String? = "",
    @SerializedName("genre_ids")
    val genreIds: List<Int>? = emptyList(),
    @SerializedName("overview")
    val overview: String? = "",
    @SerializedName("release_date")
    val releaseDate: String? = "",
    @SerializedName("backdrop_path")
    val path: String? = ""
): java.io.Serializable
