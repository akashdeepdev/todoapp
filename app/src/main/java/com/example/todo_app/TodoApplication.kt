package com.example.todo_app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TodoApplication:Application(){

    override fun onCreate() {
        super.onCreate()
    }

}