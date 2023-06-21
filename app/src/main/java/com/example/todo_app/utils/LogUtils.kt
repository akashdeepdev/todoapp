package com.example.todo_app.utils

import android.util.Log

object LogUtils {
    fun showLog(tag:String,message:String){
        Log.e(tag,message)
    }
}