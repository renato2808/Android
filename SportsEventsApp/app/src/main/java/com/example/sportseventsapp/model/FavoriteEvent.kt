package com.example.sportseventsapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_events")
data class FavoriteEvent(
    @PrimaryKey val id: String,
    val sportId: String,
    val name: String,
    val startTime: Int
)