package com.example.beesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.beesapp.data.BreweryRepository

class HomeViewModel(app: Application) : AndroidViewModel(app)  {
    private val dataRepo = BreweryRepository(app)
    val breweryData = dataRepo.breweryData
}