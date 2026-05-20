package com.tripmate.ai.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tripmate.ai.data.repository.TripRepository
import com.tripmate.ai.ui.home.HomeViewModel
import com.tripmate.ai.ui.map.NavigationViewModel
import com.tripmate.ai.ui.timeline.TimelineViewModel

class ViewModelFactory(
    private val repository: TripRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(TimelineViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TimelineViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(NavigationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NavigationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
