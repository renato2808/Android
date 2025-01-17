package com.example.sportseventsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.sportseventsapp.model.FavoriteEvent
import com.example.sportseventsapp.model.Sport
import com.example.sportseventsapp.model.SportEvent
import com.example.sportseventsapp.repository.SportsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SportsEventsViewModel(private val repository: SportsRepository?) : ViewModel() {

    val sports: StateFlow<List<Sport>> = repository?.sports ?: MutableStateFlow(emptyList())
    val favoriteEvents: StateFlow<List<FavoriteEvent>> = repository?.favoriteEvents ?: MutableStateFlow(emptyList())
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    init {
        viewModelScope.launch {
            _loading.value = true
        }
    }

    fun initializeSportsEvents() = viewModelScope.launch {
        _loading.value = true
        repository?.refreshSportsEvents()
        repository?.refreshFavoriteEvents()
        _loading.value = false
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

    class SportsEventsViewModelFactory(private val repository: SportsRepository?) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SportsEventsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SportsEventsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}