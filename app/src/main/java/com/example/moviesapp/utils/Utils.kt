package com.example.moviesapp.utils

import android.text.Selection
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import com.example.moviesapp.login.User
import com.example.moviesapp.retrofit.ApiMovie
import com.example.moviesapp.retrofit.Movie
import com.example.moviesapp.retrofit.MovieList
import com.example.moviesapp.retrofit.MovieListInfo
import com.example.moviesapp.retrofit.RetrofitClient.client
import com.example.moviesapp.retrofit.RetrofitService

fun TextView.makeLinks(vararg links: Pair<String, OnClickListener>, isUnderlineText: Boolean = false) {
    val spannableString = SpannableString(this.text)
    for (link in links) {
        val clickableSpan = object : ClickableSpan() {

            override fun updateDrawState(textPaint: TextPaint) {
                // use this to change the link color
                textPaint.color = textPaint.linkColor
                // toggle below value to enable/disable
                // the underline shown below the clickable text
                textPaint.isUnderlineText = isUnderlineText
            }

            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }
        }
        val startIndexOfLink = this.text.toString().indexOf(link.first)
        if (startIndexOfLink >= 0) {
            spannableString.setSpan(
                clickableSpan,
                startIndexOfLink,
                startIndexOfLink + link.first.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
    this.movementMethod = LinkMovementMethod.getInstance()
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}


inline fun <T> safeCall(action: () -> Result<User>): Result<User> {
    return try {
        action()
    } catch (e: Exception) {
        Failure("An unknown error occurred")
    }
}

fun getMovieListMapped(movieListInfo: MovieListInfo?, watchlist: List<ApiMovie>): MovieList {
    return MovieList(
        movieListInfo?.page ?: 0,
        movieListInfo?.totalResults ?: 0,
        movieListInfo?.totalPages ?: 0,
        getMoviesMapped(movieListInfo?.movieList ?: emptyList(), watchlist)
    )
}
private fun getMoviesMapped(movies: List<ApiMovie>, watchlist: List<ApiMovie>): List<Movie> {
    val finalList: MutableList<Movie> = mutableListOf()
    val watchlistIds = watchlist.map { it.id }
    for (movie in movies) {
        if (watchlistIds.contains(movie.id)) {
            finalList.add(getMovieMapped(movie, true))
        } else {
            finalList.add(getMovieMapped(movie, false))
        }
    }
    return finalList
}

private fun getMovieMapped(movie: ApiMovie, isFromWatchlist: Boolean): Movie {
    return Movie(
        movie.id,
        movie.voteAverage,
        movie.movieTitle,
        movie.originalLanguage,
        movie.originalTitle,
        movie.genreIds,
        movie.overview,
        movie.releaseDate,
        movie.path,
        isFromWatchlist
    )
}

val retrofitService = client!!.create(RetrofitService::class.java)

const val API_KEY = "4b828b4adcda2d74844769b54a1829cf"
const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
const val FIREBASE_REFERENCE_URL = "https://moviesapp-ffcfa-default-rtdb.europe-west1.firebasedatabase.app"
const val exemple_url = "https://api.themoviedb.org/3/movie/550?api_key=4b828b4adcda2d74844769b54a1829cf"
const val API_READ_ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0YjgyOGI0YWRjZGEyZDc0ODQ0NzY5YjU0YTE4MjljZiIsInN1YiI6IjY0NmM2Y2Q1NTRhMDk4MDEzODY1OTAyOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.kIDIsr4qJuA_J46-1dR7qnXWU1OFxm3MqZZxcMf-ba4"
const val BEARER = "Bearer "
const val TOKEN = BEARER+ API_READ_ACCESS_TOKEN