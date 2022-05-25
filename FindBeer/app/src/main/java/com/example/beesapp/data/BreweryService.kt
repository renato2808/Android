package com.example.beesapp.data

import com.example.beesapp.model.Brewery
import retrofit2.Response
import retrofit2.http.GET

interface BreweryService {
    @GET("/breweries?by_state=ohio")
    suspend fun getBreweryData(): Response<List<Brewery>>
}