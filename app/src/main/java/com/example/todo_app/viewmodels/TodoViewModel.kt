package com.example.todo_app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_app.data.models.ErrorData
import com.example.todo_app.data.models.Todo
import com.example.todo_app.data.repositories.interfaces.TodoRepository
import com.example.todo_app.utils.AppContants
import com.example.todo_app.utils.LogUtils
import com.example.todo_app.utils.Resource
import com.example.todo_app.utils.StringUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class TodoViewModel @Inject constructor(private val todoRepository: TodoRepository) : ViewModel() {

    val task = MutableLiveData<String>("")

    private val _todoListLiveData = MutableLiveData<Resource<List<Todo>?>?>(null)
    val todoListLiveData: LiveData<Resource<List<Todo>?>?> = _todoListLiveData

    private val _todoDetailsLiveData = MutableLiveData<Resource<Todo?>?>(null)
    val todoDetailsLiveData: LiveData<Resource<Todo?>?> = _todoDetailsLiveData

    init {
        getTodoList()
    }

    fun getTodoList() = viewModelScope.launch {
        _todoListLiveData.value = Resource.Loading
        LogUtils.showLog("UPDATE","UPDATING OBSERVER")

        try {
            val result = todoRepository.getTodoList()
            _todoListLiveData.value = result
        }catch (e:Exception){
            _todoListLiveData.value = Resource.Failure(ErrorData(AppContants.EXCEPTION,e.message.toString()))
        }
    }

    fun addNewTask() = viewModelScope.launch {
        _todoDetailsLiveData.value = Resource.Loading
        try {
            if(StringUtils.isStringValid(task.value!!)) {
                val todo = Todo(0, task.value!!, false)
                LogUtils.showLog("API MODEL", todo.toString())
                val result = todoRepository.addNewTask(todo)
                LogUtils.showLog("API MODEL", result.toString())
                _todoDetailsLiveData.value = result
                task.value = ""
            }else{
                _todoDetailsLiveData.value = Resource.Failure(ErrorData(AppContants.TASK,"Invalid Task"))
            }
        }catch (e:Exception){
            _todoDetailsLiveData.value = Resource.Failure(ErrorData(AppContants.EXCEPTION,e.message.toString()))
        }
    }

    fun deleteTask(id: Int) = viewModelScope.launch {
        try {
            todoRepository.deleteTask(id)
        }catch (e:Exception){
            LogUtils.showLog("DELETED_TASK",e.message.toString())
        }
    }

    fun updateTodoList(todoList: List<Todo>) {
        _todoListLiveData.value = Resource.Success(todoList)
    }
}