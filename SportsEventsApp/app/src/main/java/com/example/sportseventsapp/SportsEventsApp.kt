package com.example.sportseventsapp

import android.app.Application
import com.example.sportseventsapp.data.SportsEventsDatabase
import com.example.sportseventsapp.repository.SportsRepository

class SportsEventsApp : Application() {

    private val database by lazy { SportsEventsDatabase.getDatabase(this) }
    val repository by lazy { SportsRepository( this, database) }
}