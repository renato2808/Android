package com.example.sportseventsapp.repository

import android.app.Application
import android.util.Log
import com.example.sportseventsapp.api.RetrofitClient
import com.example.sportseventsapp.api.SportsApiService
import com.example.sportseventsapp.data.FavoriteEventsDatabase
import com.example.sportseventsapp.model.FavoriteEvent
import com.example.sportseventsapp.model.Sport
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class SportsEventsRepository(
    private val app: Application,
    private val database: FavoriteEventsDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    companion object {
        private const val TAG = "SportsEventsRepository"
    }

    private val apiService: SportsApiService = RetrofitClient.instance.create(SportsApiService::class.java)
    private val _sportsEvents = MutableStateFlow<List<Sport>>(emptyList())
    val sportsEvents: StateFlow<List<Sport>> get() = _sportsEvents

    private val _favoriteEvents = MutableStateFlow<List<FavoriteEvent>>(emptyList())
    val favoriteEvents: StateFlow<List<FavoriteEvent>> get() = _favoriteEvents

    suspend fun refreshSportsEvents() {
        withContext(dispatcher) {
            apiService.getSports().runCatching {
                execute()
            }.onFailure {
                Log.e(TAG, "Failed to call server api!")
            }.onSuccess { response ->
                val code = response.code()
                val error = response.errorBody()?.string()
                if (error != null) {
                    Log.d(TAG, "Fetch sports events $code response with error $error")
                } else {
                    Log.d(TAG, "Fetch sports events $code response.")
                }
            }.mapCatching {
                it.body()
            }.mapCatching { response ->
                val sportsEvents = response ?: emptyList()
                _sportsEvents.value = sportsEvents
            }.onSuccess {
                Log.d(TAG, "Sports events retrieved successfully: $it")
            }.onFailure {
                Log.d(TAG, "Failed fetch sports events from server: ${it.printStackTrace()}")
            }
        }
    }

    suspend fun getFavoriteEvents(): List<FavoriteEvent> {
        return withContext(dispatcher) {
            database.favoriteEventDao().getAll()
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