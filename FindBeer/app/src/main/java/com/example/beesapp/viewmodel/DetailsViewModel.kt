package com.example.beesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.beesapp.data.BreweryRepository
import kotlinx.coroutines.launch

class DetailsViewModel(private val dataRepo: BreweryRepository, app: Application) :
    AndroidViewModel(app) {
    val breweryData = dataRepo.breweryData
    val breweryRatingData = dataRepo.breweryRatingData

    fun updateBreweryRating(breweryRating: Float, nRating: Int, breweryName: String) =
        viewModelScope.launch {
            dataRepo.updateBreweryRating(breweryRating, nRating, breweryName)
        }
}

class DetailsViewModelFactory(
    private val repository: BreweryRepository,
    private val app: Application
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailsViewModel(repository, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}