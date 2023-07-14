package com.example.moviesapp.retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {
    @GET("movie/popular")
    fun getPopularMovieList(@Query("api_key") api_key: String?): Call<MovieListInfo>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movie_id: String, @Query("api_key") api_key: String?): Call<MovieDetails>

    @POST("account/{account_id}/watchlist")
    fun addToWatchList(@Header("Authorization") token: String, @Path("account_id") account_id: String, @Body body: WatchlistData): Call<ResponseBody>

    @GET("account/{account_id}/watchlist/movies")
    fun getWatchlist(@Header("Authorization") token: String, @Path("account_id") account_id: String): Call<MovieListInfo>
}