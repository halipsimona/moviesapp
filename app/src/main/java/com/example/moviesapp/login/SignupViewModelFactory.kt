package com.example.moviesapp.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SignupViewModelFactory(private val signupRepository: SignupRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            return SignupViewModel(signupRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}