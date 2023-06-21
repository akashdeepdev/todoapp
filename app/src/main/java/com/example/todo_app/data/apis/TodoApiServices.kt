package com.example.todo_app.data.apis

import com.example.todo_app.data.models.Todo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoApiServices {

    @GET("todos")
    suspend fun getTodoListFromAPI():Response<List<Todo>>

    @GET("todos/{id}")
    suspend fun getTodoFromAPI(@Path("id") id:Int):Response<Todo>

    @POST("todos")
    suspend fun addNewTaskToAPI(@Body todo: Todo):Response<Todo>


    @DELETE("todos/{id}")
    fun deleteTaskFromAPI(@Path("id") id: Int)
}