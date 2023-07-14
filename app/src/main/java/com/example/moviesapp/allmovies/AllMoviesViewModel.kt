package com.example.moviesapp.allmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.retrofit.MovieListInfo
import com.example.moviesapp.utils.Failure
import com.example.moviesapp.utils.Success
import com.example.moviesapp.utils.getMovieListMapped
import com.example.moviesapp.watchlist.WatchlistRepository
import kotlinx.coroutines.launch

class AllMoviesViewModel(
    private val allMoviesRepository: AllMoviesRepository,
    private val watchlistRepository: WatchlistRepository
) : ViewModel() {
    private val mutableLD = MutableLiveData<com.example.moviesapp.utils.Result<MovieViewState>>()

    val liveData: LiveData<com.example.moviesapp.utils.Result<MovieViewState>>
        get() = mutableLD

    private var movieListInfo: MovieListInfo? = null

    fun getPopularMovieList(accountId: String) {
        viewModelScope.launch {
            allMoviesRepository.getPopularMovieList().collect {
                if (it is Success) {
                    movieListInfo = it.data
                    getWatchlist(accountId)
                } else {
                    mutableLD.value = Failure(it.toString())
                }
            }
        }
    }

    private fun getWatchlist(accountId: String) {
        viewModelScope.launch {
            watchlistRepository.getWatchlist(accountId).collect {
                if (it is Success) {
                    val movieList = getMovieListMapped(movieListInfo, it.data.movieList)
                    mutableLD.value = Success(MovieViewState(movieList = movieList, movieDetails = null))
                } else {
                    mutableLD.value = Failure(it.toString())
                }
            }
        }
    }

    fun getMovieDetails(movieId: String) {
        viewModelScope.launch {
            allMoviesRepository.getMovieDetails(movieId).collect {
                if (it is Success) {
                    mutableLD.value = Success(MovieViewState(movieDetails = it.data, movieList = null))
                } else {
                    mutableLD.value = Failure(it.toString())
                }
            }
        }
    }

    fun addToWatchList(accountId: String, movieId: String) {
        viewModelScope.launch {
            allMoviesRepository.addToWatchList(accountId, movieId).collect {
                if (it is Success) {
                    mutableLD.value = Success(MovieViewState(addedToWatchList = true, movieDetails = null, movieList = null))
                } else {
                    mutableLD.value = Failure(it.toString())
                }
            }
        }
    }
}