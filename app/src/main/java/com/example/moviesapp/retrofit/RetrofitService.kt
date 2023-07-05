package com.example.moviesapp.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {
    @GET("movie/popular")
    fun getPopularMovieList(@Query("api_key") api_key: String?): Call<MovieListInfo>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movie_id: String, @Query("api_key") api_key: String?): Call<MovieDetails>
}