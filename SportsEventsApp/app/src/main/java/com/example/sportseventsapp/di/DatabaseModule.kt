package com.example.sportseventsapp.di

import android.content.Context
import androidx.room.Room
import com.example.sportseventsapp.data.FavoriteEventDao
import com.example.sportseventsapp.data.SportsDao
import com.example.sportseventsapp.data.SportsEventsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): SportsEventsDatabase {
        return Room.databaseBuilder(
            appContext,
            SportsEventsDatabase::class.java,
            "sports_events_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideSportsDao(database: SportsEventsDatabase): SportsDao = database.sportsDao()

    @Provides
    @Singleton
    fun provideFavoriteEventDao(database: SportsEventsDatabase): FavoriteEventDao = database.favoriteEventDao()
}
