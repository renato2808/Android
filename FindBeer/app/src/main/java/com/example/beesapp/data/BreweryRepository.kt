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
import com.example.beesapp.model.BreweryRating
import com.example.beesapp.model.BreweryRatingDAO
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class BreweryRepository(private val breweryRatingDAO: BreweryRatingDAO, val app: Application) {

    val breweryData = MutableLiveData<List<Brewery>>()
    val breweryRatingData = MutableLiveData<List<BreweryRating>>()

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
                    val breweriesRating = mutableListOf<BreweryRating>()
                    runBlocking {
                        val job = CoroutineScope(SupervisorJob()).launch {
                            for (brewery in breweries) {
                                Log.i(LOG_TAG, brewery.name)
                                if (breweryRatingDAO.get(brewery.name) == null) {
                                    insertBreweryRating(BreweryRating(brewery.name, 0f, 0))
                                }
                                val breweryRating = getBreweryRating(brewery.name)
                                val breweryName = breweryRating?.name
                                val rating = breweryRating?.rating
                                val nRatings = breweryRating?.nRatings
                                if (breweryName != null && rating != null && nRatings != null) {
                                    breweriesRating.add(BreweryRating(breweryName, rating, nRatings))
                                }
                            }
                        }
                        job.join()
                    }

                    Log.i(LOG_TAG, "breweryRatingData update finished")

                    breweryRatingData.value = breweriesRating
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

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertBreweryRating(breweryRating: BreweryRating) {
        breweryRatingDAO.insert(breweryRating)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getBreweryRating(breweryName: String): BreweryRating? {
        return breweryRatingDAO.get(breweryName)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateBreweryRating(breweryRating: Float, nRating: Int, breweryName: String) {
        val breweryRatings = breweryRatingData.value ?: emptyList()
        breweryRatings.forEach {
            if (it.name == breweryName) {
                it.rating = breweryRating
                it.nRatings = nRating
            }
        }
        if (breweryRatings.isNotEmpty()) {
            breweryRatingData.postValue(breweryRatings)
        }
        CoroutineScope(SupervisorJob()).launch {
            breweryRatingDAO.update(breweryRating, breweryName)
            breweryRatingDAO.update(nRating, breweryName)
        }
    }
}