package com.tripmate.ai.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tripmate.ai.data.local.entity.TripEntity
import com.tripmate.ai.data.repository.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: TripRepository
) : ViewModel() {

    val recentTrips: StateFlow<List<TripEntity>> = repository.getAllTrips()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()
    
    private val _generatedTripId = MutableStateFlow<Long?>(null)
    val generatedTripId = _generatedTripId.asStateFlow()

    fun generateTrip(prompt: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _generatedTripId.value = null
            
            val result = repository.generateAndSaveTrip(prompt)
            if (result.isSuccess) {
                _generatedTripId.value = result.getOrNull()
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Failed to generate trip."
            }
            _isLoading.value = false
        }
    }
    
    fun clearError() {
        _error.value = null
    }
    
    fun clearGeneratedTripId() {
        _generatedTripId.value = null
    }
}
