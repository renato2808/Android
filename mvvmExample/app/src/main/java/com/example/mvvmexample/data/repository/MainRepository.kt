package com.example.mvvmexample.data.repository

import com.example.mvvmexample.data.api.ApiHelper
import com.example.mvvmexample.data.model.User
import io.reactivex.Single

class MainRepository(private val apiHelper: ApiHelper) {

    fun getUsers(): Single<List<User>> {
        return apiHelper.getUsers()
    }

}