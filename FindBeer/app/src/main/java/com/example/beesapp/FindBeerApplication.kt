package com.example.beesapp

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.beesapp.data.BreweryRepository
import com.example.beesapp.model.BreweryRatingDatabase

private const val LAST_STATE_SELECTED = "last_state_selected"

class FindBeerApplication : Application() {
    private val Context.dataStore by preferencesDataStore(
        name = LAST_STATE_SELECTED
    )
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { BreweryRatingDatabase.getDatabase(this) }
    val repository by lazy { BreweryRepository(database.wordDao(), this, dataStore) }
}