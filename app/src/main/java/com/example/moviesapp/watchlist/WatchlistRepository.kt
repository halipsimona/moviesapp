package com.example.moviesapp.watchlist

import com.example.moviesapp.retrofit.ApiMovie
import com.example.moviesapp.retrofit.MovieListInfo
import com.example.moviesapp.utils.Failure
import com.example.moviesapp.utils.Result
import com.example.moviesapp.utils.Success
import com.example.moviesapp.utils.TOKEN
import com.example.moviesapp.utils.retrofitService
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import retrofit2.awaitResponse

private const val PAGE = "page"
private const val TOTAL_PAGES = "total_pages"
private const val TOTAL_RESULTS = "total_results"
private const val MOVIE_LIST = "movieList"
private const val WATCHLIST = "watchlist"

class WatchlistRepository(
    private val database: FirebaseDatabase
) {

    private suspend fun retrofitGetWatchlist(accountId: String) = retrofitService.getWatchlist(TOKEN, accountId).run {
        val response = awaitResponse()
        if (response.isSuccessful) {
            response.body()?.let {
                writeToDatabase(it)
            }
        } else {
            Failure("could not fetch")
        }
    }

    private suspend fun writeToDatabase(movieListInfo: MovieListInfo): Result<MovieListInfo> {
        val tableRef = database.reference.child(WATCHLIST)
        tableRef.child(PAGE).setValue(movieListInfo.page)
        tableRef.child(TOTAL_PAGES).setValue(movieListInfo.totalPages)
        tableRef.child(TOTAL_RESULTS).setValue(movieListInfo.totalResults)
        for (movie in movieListInfo.movieList) {
            tableRef.child(MOVIE_LIST).child(movie.id.toString()).setValue(movie)
        }
        return readFromDataBase()
    }

    private suspend fun readFromDataBase() = database.reference.child(WATCHLIST).get().run {
        val response = await()
        if (response.exists()) {
            val page = response.child(PAGE).value.toString().toInt()
            val totalPages = response.child(TOTAL_PAGES).value.toString().toInt()
            val totalResults = response.child(TOTAL_RESULTS).value.toString().toInt()
            val movieList = mutableListOf<ApiMovie>()
            response.child(MOVIE_LIST).children.forEach {
                val movie = it.getValue(ApiMovie::class.java)
                movie?.let { movieList.add(movie) }
            }
            Success(MovieListInfo(page, totalResults, totalPages, movieList))
        } else {
            Failure("could not fetch")
        }
    }

    suspend fun getWatchlist(accountId: String) = flow {
        emit(retrofitGetWatchlist(accountId))
    }
}