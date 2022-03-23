package com.example.mvvmexample.data.api

import com.example.mvvmexample.data.model.User
import io.reactivex.Single

interface ApiService {

    fun getUsers(): Single<List<User>>

}