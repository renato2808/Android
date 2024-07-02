package com.example.beesapp.api

import com.example.beesapp.model.Brewery
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BreweryService {
    @GET("/breweries")
    fun getBreweryData(): Response<List<Brewery>>

    @GET("/breweries")
    fun getBreweryDataByState(@Query("by_state") state: String?) : Observable<List<Brewery>?>
}