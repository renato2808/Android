package com.example.beesapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.beesapp.data.BreweryRepository

class HomeViewModel : ViewModel() {
    private val dataRepo = BreweryRepository()
    val breweryData = dataRepo.breweryData
}