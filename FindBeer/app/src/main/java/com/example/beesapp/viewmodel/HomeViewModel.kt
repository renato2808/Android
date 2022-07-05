package com.example.beesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.beesapp.data.BreweryRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val dataRepo: BreweryRepository, app: Application) : AndroidViewModel(app)  {
    val breweryData = dataRepo.breweryData
    val breweryRatingData = dataRepo.breweryRatingData

    fun changeState(state: String) = viewModelScope.launch {
        dataRepo.getBreweriesByState(state)
    }

    fun clearDisposables(){
        dataRepo.clearDisposables()
    }
}

class HomeViewModelFactory(private val repository: BreweryRepository, private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}