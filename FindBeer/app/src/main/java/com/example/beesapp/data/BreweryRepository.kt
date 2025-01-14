package com.example.beesapp.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.beesapp.LOG_TAG
import com.example.beesapp.api.BreweryService
import com.example.beesapp.model.Brewery
import com.example.beesapp.model.BreweryRating
import com.example.beesapp.model.BreweryRatingDAO
import com.example.beesapp.model.LastState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

class BreweryRepository(private val breweryRatingDAO: BreweryRatingDAO, val app: Application, private val lastState: DataStore<Preferences>) {

    private object PreferencesKeys {
        val STATE = stringPreferencesKey("state")
    }

    private val apiClient = RetrofitClient.retrofit.create(BreweryService::class.java)
    private val mutableBreweryData = MutableLiveData<List<Brewery>>()
    val breweryData: LiveData<List<Brewery>> = mutableBreweryData
    private val mutableBreweryRatingData = MutableLiveData<List<BreweryRating>>()
    val breweryRatingData: LiveData<List<BreweryRating>> = mutableBreweryRatingData

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val stateFlow : Flow<LastState> = lastState.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            // Get the the last state selected from preferences
            val state = preferences[PreferencesKeys.STATE] ?: "Alabama"
            LastState(state)
        }

    // Gets all breweries
    @WorkerThread
    fun getBreweries() {
        if (networkAvailable()) {
            val serviceData = apiClient.getBreweryData().body() ?: emptyList()
            for (brewery in serviceData) {
                Log.i(LOG_TAG, brewery.name)
            }
            mutableBreweryData.postValue(serviceData)
        }
    }

    @WorkerThread
    fun getBreweriesByState(state: String) {
        if (networkAvailable()) {
            compositeDisposable.clear()
            compositeDisposable.add(
                apiClient.getBreweryDataByState(state).observeOn(AndroidSchedulers.mainThread())
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
                        mutableBreweryRatingData.value = response
                        mutableBreweryData.postValue(breweries)
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

    suspend fun updateLastSelectedState(state: String){
        lastState.edit { preferences ->
            // Get the current State
            preferences[PreferencesKeys.STATE] = state
        }
    }

    @Suppress("DEPRECATION")
    private fun networkAvailable(): Boolean {
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }

    @WorkerThread
    suspend fun insertBreweryRating(breweryRating: BreweryRating) {
        breweryRatingDAO.insert(breweryRating)
    }

    @WorkerThread
    suspend fun getBreweryRating(breweryName: String): BreweryRating? {
        return breweryRatingDAO.get(breweryName)
    }

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
            mutableBreweryRatingData.postValue(breweryRatings)
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