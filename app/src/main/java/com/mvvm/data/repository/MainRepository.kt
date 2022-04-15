package com.mvvm.data.repository

import com.mvvm.data.api.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getAllUsers() = retrofitService.getAllUsers()

}