package com.example.moviesapp.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.utils.Result
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SignupViewModel(
    private val signupRepository: SignupRepository
) : ViewModel() {
    private var signupJob: Job? = null

    private val mutableLD = MutableLiveData<Result<User>>()
    val signupLD: LiveData<Result<User>>
        get() = mutableLD

    fun signUp(email: String, password: String) {
        // signupJob.cancelIfActive()
        signupJob = viewModelScope.launch {
            val result = signupRepository.signUp(email, password)
            mutableLD.value = result
        }
    }
}