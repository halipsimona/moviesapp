package com.example.moviesapp.utils

import androidx.annotation.Keep

@Keep
sealed class Result<out T : Any>

@Keep
class Success<out T : Any>(val data: T) : Result<T>()

@Keep
class Failure(
    val message: String
) : Result<Nothing>()