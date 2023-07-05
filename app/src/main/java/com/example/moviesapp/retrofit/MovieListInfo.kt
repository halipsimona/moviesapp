package com.example.moviesapp.retrofit

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MovieListInfo(
    @SerializedName("page") val page: Int,
    @SerializedName("total_results") val totalResults : Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val movieList: List<ApiMovie>
): java.io.Serializable
