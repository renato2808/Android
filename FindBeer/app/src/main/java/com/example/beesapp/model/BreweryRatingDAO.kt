package com.example.beesapp.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BreweryRatingDAO {

    @Query("SELECT * FROM breweries_rating_table where name = :name")
    suspend fun get(name: String): BreweryRating?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(breweryRating: BreweryRating)

    @Query("UPDATE breweries_rating_table SET rating=:rating WHERE name = :name")
    suspend fun update(rating: Float?, name: String)

    @Query("UPDATE breweries_rating_table SET nRatings=:nRating WHERE name = :name")
    suspend fun update(nRating: Int?, name: String)

    @Query("DELETE FROM breweries_rating_table")
    suspend fun deleteAll()
}