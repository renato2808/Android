package com.example.sportseventsapp.api

import com.example.sportseventsapp.model.Sport
import retrofit2.Call
import retrofit2.http.GET

interface SportsApiService {
    @GET("sports")
    fun getSports(): Call<List<Sport>>
}