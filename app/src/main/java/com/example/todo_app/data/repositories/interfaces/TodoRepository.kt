package com.example.todo_app.data.repositories.interfaces

import com.example.todo_app.data.models.Todo
import com.example.todo_app.utils.Resource

interface TodoRepository {
    suspend fun getTodoList(): Resource<List<Todo>?>
    suspend fun addNewTask(todo: Todo): Resource<Todo?>
    fun deleteTask(id: Int)
}