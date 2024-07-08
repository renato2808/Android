package com.example.sportseventsapp

import android.app.Application
import com.example.sportseventsapp.data.FavoriteEventsDatabase
import com.example.sportseventsapp.repository.SportsEventsRepository

class SportsEventsApp : Application() {

    private val database by lazy { FavoriteEventsDatabase.getDatabase(this) }
    val repository by lazy { SportsEventsRepository( this, database) }

}