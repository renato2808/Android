package com.example.beesapp.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.example.beesapp.LOG_TAG
import com.example.beesapp.WEB_SERVICE_URL
import com.example.beesapp.model.Brewery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class BreweryRepository(val app: Application) {

    val breweryData = MutableLiveData<List<Brewery>>()

    init {
        CoroutineScope(Dispatchers.IO).launch { getBreweriesByState("Alabama") }
    }

    @WorkerThread
    suspend fun getBreweries() {
        if (networkAvailable()) {
            val retrofit = Retrofit.Builder()
                .baseUrl(WEB_SERVICE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            val service = retrofit.create(BreweryService::class.java)
            val serviceData = service.getBreweryData().body() ?: emptyList()
            for (brewery in serviceData) {
                Log.i(LOG_TAG, brewery.name)
            }
            breweryData.postValue(serviceData)
        }
    }

    @WorkerThread
    fun getBreweriesByState(state: String) {
        if (networkAvailable()) {
            val retrofit = Retrofit.Builder()
                .baseUrl(WEB_SERVICE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            val service = retrofit.create(BreweryService::class.java)
            val call: Call<List<Brewery>?> = service.getBreweryDataByState(state)
            call.enqueue(object : Callback<List<Brewery>?> {
                override fun onResponse(call: Call<List<Brewery>?>, response: Response<List<Brewery>?>) {
                    val statusCode: Int = response.code()
                    val breweries = response.body() ?: emptyList()
                    for (brewery in breweries) {
                        Log.i(LOG_TAG, brewery.name)
                    }
                    breweryData.postValue(breweries)
                }

                override fun onFailure(call: Call<List<Brewery>?>, t: Throwable) {
                    t.message?.let { Log.i(LOG_TAG, it) }
                }
            })
        }
    }

    @Suppress("DEPRECATION")
    private fun networkAvailable(): Boolean {
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }
}