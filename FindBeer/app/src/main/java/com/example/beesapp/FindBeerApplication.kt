package com.example.beesapp

import android.app.Application
import com.example.beesapp.data.BreweryRepository
import com.example.beesapp.model.BreweryRatingDatabase

class FindBeerApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { BreweryRatingDatabase.getDatabase(this) }
    val repository by lazy { BreweryRepository(database.wordDao(), this) }
}