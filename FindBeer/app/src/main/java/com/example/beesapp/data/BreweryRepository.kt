package com.example.beesapp.data

import androidx.lifecycle.MutableLiveData
import com.example.beesapp.model.Brewery

class BreweryRepository {

    val breweryData = MutableLiveData<MutableList<Brewery>>()

    init {
        getBreweryData()
    }

    private fun getBreweryData() {
        breweryData.value = DataSource.getBreweries()
    }
}