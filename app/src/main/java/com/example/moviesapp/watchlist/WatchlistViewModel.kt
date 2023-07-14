package com.example.moviesapp.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.utils.Failure
import com.example.moviesapp.utils.Result
import com.example.moviesapp.utils.Success
import com.example.moviesapp.utils.getMovieListMapped
import kotlinx.coroutines.launch

class WatchlistViewModel (
    private val watchlistRepository: WatchlistRepository
): ViewModel() {
    private val mutableLD = MutableLiveData<Result<WatchlistViewState>>()

    val liveData: LiveData<Result<WatchlistViewState>>
        get() = mutableLD

    fun getWatchlist(accountId: String) {
        viewModelScope.launch {
            watchlistRepository.getWatchlist(accountId).collect {
                if (it is Success) {
                    mutableLD.value = Success(WatchlistViewState(movieList = getMovieListMapped(it.data, it.data.movieList)))
                } else {
                    mutableLD.value = Failure(it.toString())
                }
            }
        }
    }

}