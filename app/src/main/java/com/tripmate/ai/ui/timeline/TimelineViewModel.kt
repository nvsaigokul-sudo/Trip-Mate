package com.tripmate.ai.ui.timeline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tripmate.ai.data.local.entity.ItineraryDayEntity
import com.tripmate.ai.data.local.entity.PlaceEntity
import com.tripmate.ai.data.local.entity.TripEntity
import com.tripmate.ai.data.repository.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TimelineViewModel(
    private val repository: TripRepository
) : ViewModel() {

    private val _trip = MutableStateFlow<TripEntity?>(null)
    val trip = _trip.asStateFlow()

    private val _days = MutableStateFlow<List<DayWithPlaces>>(emptyList())
    val days = _days.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun loadTrip(tripId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            val loadedTrip = repository.getTripById(tripId)
            _trip.value = loadedTrip

            if (loadedTrip != null) {
                val itineraryDays = repository.getDaysForTrip(tripId)
                val mappedDays = itineraryDays.map { day ->
                    val places = repository.getPlacesForDay(day.dayId)
                    DayWithPlaces(day, places)
                }
                _days.value = mappedDays
            }
            _isLoading.value = false
        }
    }
}

data class DayWithPlaces(
    val day: ItineraryDayEntity,
    val places: List<PlaceEntity>
)
