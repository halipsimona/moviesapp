package com.example.moviesapp.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.utils.Result
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {
    private var signInJob: Job? = null

    var mutableLD = MutableLiveData<Result<User>>()
    val signinLD: LiveData<Result<User>>
        get() = mutableLD


    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            val result = loginRepository.signIn(email, password)
            mutableLD.value = result
        }
    }
}