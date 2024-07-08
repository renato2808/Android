package com.example.sportseventsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.sportseventsapp.model.SportEvent
import com.example.sportseventsapp.model.FavoriteEvent
import com.example.sportseventsapp.model.Sport
import com.example.sportseventsapp.repository.SportsEventsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SportsEventsViewModel(private val repository: SportsEventsRepository?) : ViewModel() {

    val sportsEvents: StateFlow<List<Sport>> = repository?.sportsEvents ?: MutableStateFlow(emptyList())
    val favoriteEvents: StateFlow<List<FavoriteEvent>> = repository?.favoriteEvents ?: MutableStateFlow(emptyList())

    fun initializeSportsEvents() = viewModelScope.launch {
        repository?.refreshSportsEvents()
        repository?.refreshFavoriteEvents()
    }

    fun addFavorite(sportEvent: SportEvent) {
        viewModelScope.launch {
            repository?.addFavorite(
                FavoriteEvent(
                    id = sportEvent.id,
                    sportId = sportEvent.sportId,
                    name = sportEvent.name,
                    startTime = sportEvent.startTime
                )
            )
        }
    }

    fun removeFavorite(sportEvent: SportEvent) {
        viewModelScope.launch {
            repository?.removeFavorite(
                FavoriteEvent(
                    id = sportEvent.id,
                    sportId = sportEvent.sportId,
                    name = sportEvent.name,
                    startTime = sportEvent.startTime
                )
            )
        }
    }

    class SportsEventsViewModelFactory(private val repository: SportsEventsRepository?) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SportsEventsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SportsEventsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}