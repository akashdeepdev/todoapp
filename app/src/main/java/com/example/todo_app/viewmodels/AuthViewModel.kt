package com.example.todo_app.viewmodels

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_app.R
import com.example.todo_app.data.repositories.interfaces.AuthRepository
import com.example.todo_app.ui.fragments.auth.LoginFragment
import com.example.todo_app.utils.LogUtils
import com.example.todo_app.utils.Resource
import com.example.todo_app.utils.StringUtils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    val email = MutableLiveData<String>("")
    val fullname = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")


    private val _loginLiveData = MutableLiveData<Resource<FirebaseUser>?>(null)
    val loginLiveData: LiveData<Resource<FirebaseUser>?> = _loginLiveData

    private val _signupLiveData = MutableLiveData<Resource<FirebaseUser>?>(null)
    val signupLiveData: LiveData<Resource<FirebaseUser>?> = _signupLiveData

    val currentUser: FirebaseUser?
        get() = repository.currentUser

    init {
        if (repository.currentUser != null) {
            _loginLiveData.value = Resource.Success(repository.currentUser!!)
        }
    }

    fun loginUser() = viewModelScope.launch {
        _loginLiveData.value = Resource.Loading
        val userEmail = email.value!!
        val pass = password.value!!
        if(StringUtils.isEmailValid(userEmail) && StringUtils.isPasswordValid(pass)) {
            val result = repository.login(userEmail, pass)
            _loginLiveData.value = result
        }else{
            _loginLiveData.value = Resource.Failure(Exception("Invalid Email or Password"))
        }
    }

    fun signupUser() = viewModelScope.launch {
        _signupLiveData.value = Resource.Loading
        val userEmail = email.value!!
        val pass = password.value!!
        val name = fullname.value!!

        StringUtils.apply {
            if(isEmailValid(userEmail) && isPasswordValid(pass) && isStringValid(name)) {
                val result = repository.signup(name, userEmail, pass)
                _signupLiveData.value = result
            }else{
                _signupLiveData.value = Resource.Failure(Exception("Invalid data"))
            }
        }
    }

    fun signInWithGoogle(reqCode:Int,data:Intent?)=viewModelScope.launch {
        _loginLiveData.value = Resource.Loading

        if (reqCode == LoginFragment.RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!

                val result = repository.signInWithGoogle(account.idToken!!)
                LogUtils.showLog("GOOGLE RESULT",result.toString())
                _loginLiveData.value = result
            } catch (e: ApiException) {
                _loginLiveData.value = Resource.Failure(e)
            }
        }
    }

    fun logout() {
        repository.logout()
        _loginLiveData.value = null
        _signupLiveData.value = null
    }
}