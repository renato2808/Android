package com.example.beesapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breweries_rating_table")
data class BreweryRating(@PrimaryKey val name: String, @ColumnInfo var rating: Float, @ColumnInfo var nRatings: Int)
