package com.example.moviesapp.retrofit

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Genre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
): java.io.Serializable
