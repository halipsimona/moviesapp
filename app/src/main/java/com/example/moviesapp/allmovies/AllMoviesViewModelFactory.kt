package com.example.moviesapp.allmovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.watchlist.WatchlistRepository

class AllMoviesViewModelFactory(private val allMoviesRepository: AllMoviesRepository, private val watchlistRepository: WatchlistRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllMoviesViewModel::class.java)) {
            return AllMoviesViewModel(allMoviesRepository, watchlistRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}