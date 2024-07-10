package com.example.sportseventsapp.repository

import android.app.Application
import android.util.Log
import com.example.sportseventsapp.api.RetrofitClient
import com.example.sportseventsapp.api.SportsApiService
import com.example.sportseventsapp.data.SportData
import com.example.sportseventsapp.data.SportsEventsDatabase
import com.example.sportseventsapp.model.FavoriteEvent
import com.example.sportseventsapp.model.Sport
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class SportsRepository(
    private val app: Application,
    private val database: SportsEventsDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    companion object {
        private const val TAG = "SportsEventsRepository"
    }

    private val apiService: SportsApiService = RetrofitClient.instance.create(SportsApiService::class.java)
    private val _sports = MutableStateFlow<List<Sport>>(emptyList())
    val sports: StateFlow<List<Sport>> get() = _sports
    private val _favoriteEvents = MutableStateFlow<List<FavoriteEvent>>(emptyList())
    val favoriteEvents: StateFlow<List<FavoriteEvent>> get() = _favoriteEvents

    suspend fun refreshSportsEvents() {
        withContext(dispatcher) {
            apiService.getSports().runCatching {
                execute()
            }.onFailure {
                Log.e(TAG, "Failed to call server api! Fetching from database...")
                val sports = database.sportsDao().getAllSports()
                _sports.value = sports.map { Sport.fromJson(it.data) }
            }.onSuccess { response ->
                val code = response.code()
                val error = response.errorBody()?.string()
                if (error != null) {
                    Log.d(TAG, "Fetch sports events $code response with error $error")
                    val sports = database.sportsDao().getAllSports()
                    _sports.value = sports.map { Sport.fromJson(it.data) }
                } else {
                    Log.d(TAG, "Fetch sports events $code response.")
                    val sports = response.body() ?: emptyList()
                    _sports.value = sports

                    // Save the fetched events to the database
                    database.sportsDao().deleteAll()
                    sports.forEach { sport ->
                        database.sportsDao().insertSport(SportData(data = sport.toJson()))
                    }
                }
            }
        }
    }

    suspend fun addFavorite(event: FavoriteEvent) {
        withContext(dispatcher) {
            database.favoriteEventDao().insert(event)
        }
        refreshFavoriteEvents()
    }

    suspend fun removeFavorite(event: FavoriteEvent) {
        withContext(dispatcher) {
            database.favoriteEventDao().deleteById(event.id)
        }
        refreshFavoriteEvents()
    }

    suspend fun refreshFavoriteEvents() {
        withContext(dispatcher) {
            _favoriteEvents.value = database.favoriteEventDao().getAll()
        }
    }
}