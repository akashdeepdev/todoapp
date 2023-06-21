package com.example.todo_app.utils

object StringUtils {
    fun isEmailValid(email:String):Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isNameInvalid(name:String):String?{
        if(name.trim().length<4){
            return "Name must be min 4 Characters"
        }
        if(name.contains("[0-9]".toRegex())){
            return "Name must be String type"
        }
        return null
    }

    fun isStringValid(str:String):Boolean{
        return !str.trim().isNullOrEmpty()
    }

    fun isPasswordInvalid(password:String):String?{
        if(password.trim().length<7){
            return "Password must be min 7 characters"
        }
        return null
    }
}