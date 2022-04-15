package com.mvvm.data.api

import com.mvvm.data.model.User
import com.mvvm.data.model.UserList
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("users")
    suspend fun getAllUsers(): Response<ArrayList<UserList>>

    @GET("users/{login}")
    suspend fun getUser(
        @Path("login") login: String
    ): Response<User>

    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance(): RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}