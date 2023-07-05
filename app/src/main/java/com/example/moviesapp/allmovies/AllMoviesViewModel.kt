package com.example.moviesapp.allmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.utils.Failure
import com.example.moviesapp.utils.Success
import kotlinx.coroutines.launch

class AllMoviesViewModel(
    private val allMoviesRepository: AllMoviesRepository
): ViewModel() {
    private val mutableLD = MutableLiveData<com.example.moviesapp.utils.Result<MovieViewState>>()

    val liveData: LiveData<com.example.moviesapp.utils.Result<MovieViewState>>
        get() = mutableLD

    fun getPopularMovieList() {
        viewModelScope.launch {
            allMoviesRepository.getPopularMovieList().collect {
                if (it is Success) {
                    mutableLD.value = Success(MovieViewState(movieList = it.data, movieDetails = null))
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
}