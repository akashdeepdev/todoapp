package com.example.todo_app.data.repositories

import com.example.todo_app.data.models.ErrorData
import com.example.todo_app.data.repositories.interfaces.AuthRepository
import com.example.todo_app.utils.AppContants
import com.example.todo_app.utils.Resource
import com.example.todo_app.utils.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val auth:FirebaseAuth): AuthRepository {
    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override suspend fun login(email: String, password: String): Resource<FirebaseUser> = withContext(Dispatchers.IO) {
        return@withContext try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            Resource.Failure(ErrorData(AppContants.EXCEPTION,e.message.toString()))
        }
    }

    override suspend fun signup(
        name: String,
        email: String,
        password: String
    ): Resource<FirebaseUser> = withContext(Dispatchers.IO) {
        return@withContext try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())?.await()
            return@withContext Resource.Success(result.user!!)
        } catch (e: Exception) {
            Resource.Failure(ErrorData(AppContants.EXCEPTION,e.message.toString()))
        }
    }

    override suspend fun signInWithGoogle(idToken: String): Resource<FirebaseUser> = withContext(Dispatchers.IO) {

        return@withContext try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = auth.signInWithCredential(credential).await()
            return@withContext Resource.Success(result.user!!)
        } catch (e: Exception) {
            Resource.Failure(ErrorData(AppContants.EXCEPTION,e.message.toString()))
        }
    }

    override fun logout() {
        auth.signOut()
    }
}