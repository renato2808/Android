package com.example.sportseventsapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sportseventsapp.model.FavoriteEvent

@Database(entities = [SportData::class, FavoriteEvent::class], version = 1)
abstract class SportsEventsDatabase : RoomDatabase() {
    abstract fun favoriteEventDao(): FavoriteEventDao
    abstract fun sportsDao(): SportsDao

    companion object {
        @Volatile
        private var INSTANCE: SportsEventsDatabase? = null

        fun getDatabase(context: Context): SportsEventsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SportsEventsDatabase::class.java,
                    "sports_events_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}