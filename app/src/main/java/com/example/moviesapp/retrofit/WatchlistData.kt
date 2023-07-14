package com.example.moviesapp.retrofit

import androidx.annotation.Keep

@Keep
data class WatchlistData(
    val media_type: String,
    val media_id: Int,
    val watchlist: Boolean
): java.io.Serializable