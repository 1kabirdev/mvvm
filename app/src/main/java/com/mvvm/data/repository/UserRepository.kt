package com.mvvm.data.repository

import com.mvvm.data.api.RetrofitService

class UserRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getUser(login: String) = retrofitService.getUser(login)


}