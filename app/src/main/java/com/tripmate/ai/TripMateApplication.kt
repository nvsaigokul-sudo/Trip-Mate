package com.tripmate.ai

import android.app.Application
import com.tripmate.ai.data.local.TripDatabase
import com.tripmate.ai.data.remote.GenerativeAIService
import com.tripmate.ai.data.repository.TripRepository

class TripMateApplication : Application() {
    
    // Manual dependency injection
    lateinit var database: TripDatabase
    lateinit var aiService: GenerativeAIService
    lateinit var tripRepository: TripRepository

    override fun onCreate() {
        super.onCreate()
        
        database = TripDatabase.getDatabase(this)
        aiService = GenerativeAIService()
        tripRepository = TripRepository(database.tripDao(), aiService)
    }
}
