package com.example.todo_app.data.repositories

import com.example.todo_app.data.apis.TodoApiServices
import com.example.todo_app.data.models.ErrorData
import com.example.todo_app.data.models.Todo
import com.example.todo_app.data.repositories.interfaces.TodoRepository
import com.example.todo_app.utils.AppContants
import com.example.todo_app.utils.LogUtils
import com.example.todo_app.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(private val todoApiServices: TodoApiServices):TodoRepository {
    override suspend fun getTodoList(): Resource<List<Todo>?> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = todoApiServices.getTodoListFromAPI()
            LogUtils.showLog("API get all",response.toString())
            if(response.code()==200) {
                Resource.Success(response.body())
            }else{
                Resource.Failure(ErrorData("TodoListAPI",response.errorBody().toString()))
            }
        }catch (e:java.lang.Exception){
            Resource.Failure(ErrorData(AppContants.EXCEPTION,e.message.toString()))
        }
    }

    override suspend fun addNewTask(todo: Todo): Resource<Todo?> = withContext(Dispatchers.IO) {
        return@withContext try {
           val response = todoApiServices.addNewTaskToAPI(todo)
           if(response.code()==201) {
                Resource.Success(response.body())
            }else{
               Resource.Failure(ErrorData("TodoListAPI",response.errorBody().toString()))
            }
        }catch (e:java.lang.Exception){
            Resource.Failure(ErrorData(AppContants.EXCEPTION,e.message.toString()))
        }
    }


    override fun deleteTask(id: Int)  {
        try {
           todoApiServices.deleteTaskFromAPI(id)
        }catch (e:java.lang.Exception){
            Resource.Failure(ErrorData("Exception",e.message.toString()))
        }
    }
}