package com.example.todo_app.utils

object StringUtils {
    fun isEmailValid(email:String):Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isStringValid(str:String):Boolean{
        return !str.trim().isNullOrEmpty()
    }

    fun isPasswordValid(password:String):Boolean{
        return !password.trim().isNullOrEmpty()
    }
}