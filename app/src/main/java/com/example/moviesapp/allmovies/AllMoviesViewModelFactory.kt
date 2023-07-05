package com.example.moviesapp.allmovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AllMoviesViewModelFactory(private val allMoviesRepository: AllMoviesRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllMoviesViewModel::class.java)) {
            return AllMoviesViewModel(allMoviesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}