package com.example.beesapp.adapter

import com.example.beesapp.model.Brewery

interface OnBreweryClickListener {
    fun onBreweryClick(brewery: Brewery, rating: Float, nRatings: Int)
}