package com.example.todo_app.di

import com.example.todo_app.data.apis.TodoApiServices
import com.example.todo_app.data.repositories.AuthRepositoryImpl
import com.example.todo_app.data.repositories.TodoRepositoryImpl
import com.example.todo_app.data.repositories.interfaces.AuthRepository
import com.example.todo_app.data.repositories.interfaces.TodoRepository
import com.example.todo_app.utils.AppContants
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun getFirebaseAuthInstance():FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun getRetrofitInstance():Retrofit= Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .baseUrl(AppContants.BASE_API_URL)
        .build()

    @Singleton
    @Provides
    fun getTodoApiServices(retrofit: Retrofit):TodoApiServices = retrofit.create(TodoApiServices::class.java)

    @Provides
    fun providesAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    fun provideTodoRepository(impl: TodoRepositoryImpl): TodoRepository = impl
}