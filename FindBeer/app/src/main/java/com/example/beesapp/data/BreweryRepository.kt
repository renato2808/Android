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
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class BreweryRepository(private val breweryRatingDAO: BreweryRatingDAO, val app: Application) {

    val breweryData = MutableLiveData<List<Brewery>>()
    val breweryRatingData = MutableLiveData<List<BreweryRating>>()
    private val compositeDisposable: CompositeDisposable

    init {
        CoroutineScope(Dispatchers.IO).launch { getBreweriesByState("Alabama") }
        compositeDisposable = CompositeDisposable()
    }

    // Gets all breweries
    @WorkerThread
    fun getBreweries() {
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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            val service = retrofit.create(BreweryService::class.java)
            compositeDisposable.clear()
            compositeDisposable.add(
                service.getBreweryDataByState(state).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ response ->
                        CoroutineScope(SupervisorJob()).launch {
                            onResponse(
                                response ?: emptyList()
                            )
                        }
                    }, { t -> onFailure(t) })
            )
        }
    }


    private fun onFailure(t: Throwable) {
        t.message?.let { Log.i(LOG_TAG, it) }
    }

    private suspend fun onResponse(breweries: List<Brewery>) {
        compositeDisposable.add(
            updateBreweryStateDatabase(breweries).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response ->
                    run {
                        breweryRatingData.value = response
                        breweryData.postValue(breweries)
                    }
                }, { t -> onFailure(t) })
        )
        Log.i(LOG_TAG, "breweryRatingData update finished")
    }

    private suspend fun updateBreweryStateDatabase(breweries: List<Brewery>): Observable<List<BreweryRating>> {
        val breweriesRating = mutableListOf<BreweryRating>()
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
        return Observable.just(breweriesRating)
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

    fun clearDisposables() {
        compositeDisposable.dispose()
    }
}