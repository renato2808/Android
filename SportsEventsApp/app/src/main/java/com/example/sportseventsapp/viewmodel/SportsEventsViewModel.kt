package com.example.sportseventsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportseventsapp.model.FavoriteEvent
import com.example.sportseventsapp.model.Sport
import com.example.sportseventsapp.model.SportEvent
import com.example.sportseventsapp.repository.SportsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SportsEventsViewModel @Inject constructor(private val repository: SportsRepository) : ViewModel() {

    val sports: StateFlow<List<Sport>> = repository.sports
    val favoriteEvents: StateFlow<List<FavoriteEvent>> = repository.favoriteEvents
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    fun initializeSportsEvents() = viewModelScope.launch {
        _loading.value = true
        repository.refreshSportsEvents()
        repository.refreshFavoriteEvents()
        _loading.value = false
    }

    fun addFavorite(sportEvent: SportEvent) {
        viewModelScope.launch {
            repository.addFavorite(
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
            repository.removeFavorite(
                FavoriteEvent(
                    id = sportEvent.id,
                    sportId = sportEvent.sportId,
                    name = sportEvent.name,
                    startTime = sportEvent.startTime
                )
            )
        }
    }
}