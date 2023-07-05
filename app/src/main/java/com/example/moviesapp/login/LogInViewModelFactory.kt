package com.example.moviesapp.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LogInViewModelFactory(private val loginRepository: LoginRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(loginRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}