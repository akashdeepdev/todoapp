package com.example.todo_app.utils

import com.example.todo_app.data.models.ErrorData

sealed class Resource<out R> {
    data class Success<out R>(val result: R) : Resource<R>()
    data class Failure(val error: ErrorData) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}
