package com.example.moviesapp.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WatchlistViewModelFactory(private val watchlistRepository: WatchlistRepository) : ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WatchlistViewModel::class.java)) {
            return WatchlistViewModel(watchlistRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}