package com.example.todo_app.data.models

import com.google.gson.annotations.SerializedName

data class Todo(
    @SerializedName("id")
    val id:Int,

    @SerializedName("title")
    var task:String,

    @SerializedName("completed")
    var completed:Boolean
)
