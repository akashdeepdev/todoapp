package com.example.todo_app.data.repositories.interfaces

import com.example.todo_app.utils.Resource
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Resource<FirebaseUser>
    suspend fun signup(name: String, email: String, password: String): Resource<FirebaseUser>
    suspend fun signInWithGoogle(idToken:String): Resource<FirebaseUser>
    fun logout()
}