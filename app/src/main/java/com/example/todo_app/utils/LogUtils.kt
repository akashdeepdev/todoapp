package com.example.todo_app.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

object LogUtils {
    fun showLog(tag:String,message:String){
        Log.e(tag,message)
    }

    fun showToast(context:Context,message: String){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }
}