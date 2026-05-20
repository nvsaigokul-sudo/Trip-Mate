package com.tripmate.ai.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.tripmate.ai.data.local.entity.ItineraryDayEntity
import com.tripmate.ai.data.local.entity.PlaceEntity
import com.tripmate.ai.data.local.entity.TripEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrip(trip: TripEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDay(day: ItineraryDayEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaces(places: List<PlaceEntity>)

    @Query("SELECT * FROM trips ORDER BY createdAt DESC")
    fun getAllTrips(): Flow<List<TripEntity>>

    @Query("SELECT * FROM trips WHERE id = :tripId")
    suspend fun getTripById(tripId: Long): TripEntity?

    @Query("SELECT * FROM itinerary_days WHERE tripId = :tripId ORDER BY dayNumber ASC")
    suspend fun getDaysForTrip(tripId: Long): List<ItineraryDayEntity>

    @Query("SELECT * FROM places WHERE dayId = :dayId")
    suspend fun getPlacesForDay(dayId: Long): List<PlaceEntity>

    @Query("UPDATE trips SET isFavorite = :isFavorite WHERE id = :tripId")
    suspend fun updateTripFavoriteStatus(tripId: Long, isFavorite: Boolean)
}
