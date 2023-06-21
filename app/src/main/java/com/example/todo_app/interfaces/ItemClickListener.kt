package com.example.todo_app.interfaces

import com.example.todo_app.data.models.Todo

interface ItemClickListener {
    fun onClickItemCard(item:Todo,position:Int)
}