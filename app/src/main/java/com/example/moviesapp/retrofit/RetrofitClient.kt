package com.example.moviesapp.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val baseUrl = "https://api.themoviedb.org/3/"
    private var retrofit: Retrofit? = null
    val client: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create()).build()
            }
            return retrofit
        }
}