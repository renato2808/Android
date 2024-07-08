package com.example.sportseventsapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sportseventsapp.model.FavoriteEvent

@Dao
interface FavoriteEventDao {
    @Query("SELECT * FROM favorite_events")
    fun getAll(): List<FavoriteEvent>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteEvent: FavoriteEvent)

    @Delete
    fun delete(favoriteEvent: FavoriteEvent): Int

    @Query("DELETE FROM favorite_events WHERE id = :id")
    fun deleteById(id: String): Int // Changed return type to Int for number of deleted rows
}