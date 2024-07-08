package com.example.sportseventsapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SportsDao {
    @Query("SELECT * FROM sports")
    suspend fun getAllSports(): List<SportData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSport(sport: SportData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sports: List<SportData>)

    @Query("DELETE FROM sports")
    suspend fun deleteAll()
}

