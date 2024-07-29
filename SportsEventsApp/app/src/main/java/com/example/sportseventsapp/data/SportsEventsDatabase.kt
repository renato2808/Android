package com.example.sportseventsapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sportseventsapp.model.FavoriteEvent

@Database(entities = [SportData::class, FavoriteEvent::class], version = 1)
abstract class SportsEventsDatabase : RoomDatabase() {
    abstract fun favoriteEventDao(): FavoriteEventDao
    abstract fun sportsDao(): SportsDao
}