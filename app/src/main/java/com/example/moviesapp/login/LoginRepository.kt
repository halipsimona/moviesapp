package com.example.moviesapp.login

import com.example.moviesapp.utils.Success
import com.example.moviesapp.utils.Result
import com.example.moviesapp.utils.safeCall
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginRepository {
    private val auth = Firebase.auth

    suspend fun signIn(email: String, password: String)  =
        firebaseSignInWithGoogle(email, password)

    private suspend fun firebaseSignInWithGoogle(email: String, password: String): Result<User> {
        return withContext(Dispatchers.IO){
            safeCall<User> {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                val uid = result.user!!.uid
                Success(User(uid, email, password))
            }
        }
    }
}