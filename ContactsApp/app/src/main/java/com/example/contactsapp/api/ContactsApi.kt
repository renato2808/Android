package com.example.contactsapp.api

import com.example.contactsapp.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ContactsApi {
    @GET("api/1.0/")
    fun getContacts(
        @Query("seed") seed: String,
        @Query("results") results: Int,
        @Query("page") page: Int
    ): Call<ApiResponse>
}