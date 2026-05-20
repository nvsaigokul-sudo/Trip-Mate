package com.tripmate.ai.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tripmate.ai.data.local.entity.PlaceEntity
import com.tripmate.ai.data.repository.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint

class NavigationViewModel(
    private val repository: TripRepository
) : ViewModel() {

    private val _currentLocation = MutableStateFlow<GeoPoint?>(null)
    val currentLocation = _currentLocation.asStateFlow()

    private val _routeWaypoints = MutableStateFlow<List<GeoPoint>>(emptyList())
    val routeWaypoints = _routeWaypoints.asStateFlow()
    
    private val _places = MutableStateFlow<List<PlaceEntity>>(emptyList())
    val places = _places.asStateFlow()

    fun loadDayRoute(dayId: Long) {
        viewModelScope.launch {
            val dayPlaces = repository.getPlacesForDay(dayId)
            _places.value = dayPlaces
            
            val points = dayPlaces.map { GeoPoint(it.latitude, it.longitude) }
            _routeWaypoints.value = points
            
            if (points.isNotEmpty() && _currentLocation.value == null) {
                _currentLocation.value = points.first() // Simulate starting at the first place as fallback
            }
        }
    }

    fun updateCurrentLocation(lat: Double, lon: Double) {
        _currentLocation.value = GeoPoint(lat, lon)
    }
}
