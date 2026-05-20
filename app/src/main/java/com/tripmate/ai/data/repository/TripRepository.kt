package com.tripmate.ai.data.repository

import com.tripmate.ai.data.local.dao.TripDao
import com.tripmate.ai.data.local.entity.ItineraryDayEntity
import com.tripmate.ai.data.local.entity.PlaceEntity
import com.tripmate.ai.data.local.entity.TripEntity
import com.tripmate.ai.data.remote.GenerativeAIService
import kotlinx.coroutines.flow.Flow

class TripRepository(
    private val tripDao: TripDao,
    private val aiService: GenerativeAIService
) {
    
    fun getAllTrips(): Flow<List<TripEntity>> = tripDao.getAllTrips()

    suspend fun getTripById(tripId: Long): TripEntity? = tripDao.getTripById(tripId)
    
    suspend fun getDaysForTrip(tripId: Long): List<ItineraryDayEntity> = tripDao.getDaysForTrip(tripId)
    
    suspend fun getPlacesForDay(dayId: Long): List<PlaceEntity> = tripDao.getPlacesForDay(dayId)

    suspend fun generateAndSaveTrip(prompt: String): Result<Long> {
        val aiResult = aiService.generateTripPlan(prompt)
        
        return if (aiResult.isSuccess) {
            val response = aiResult.getOrThrow()
            
            // 1. Save Trip
            val trip = TripEntity(
                prompt = prompt,
                title = "Trip to ${response.destination}",
                destination = response.destination,
                budget = response.budget,
                durationDays = response.durationDays
            )
            val tripId = tripDao.insertTrip(trip)
            
            // 2. Save Days and Places
            response.itinerary.forEach { dayPlan ->
                val day = ItineraryDayEntity(
                    tripId = tripId,
                    dayNumber = dayPlan.day,
                    description = dayPlan.description
                )
                val dayId = tripDao.insertDay(day)
                
                val places = dayPlan.places.map { placePlan ->
                    PlaceEntity(
                        dayId = dayId,
                        name = placePlan.name,
                        description = placePlan.description,
                        latitude = placePlan.latitude,
                        longitude = placePlan.longitude,
                        timeHint = placePlan.timeHint,
                        type = placePlan.type
                    )
                }
                tripDao.insertPlaces(places)
            }
            
            Result.success(tripId)
        } else {
            Result.failure(aiResult.exceptionOrNull() ?: Exception("Unknown error"))
        }
    }
}
