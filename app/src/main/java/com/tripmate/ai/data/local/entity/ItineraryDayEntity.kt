package com.tripmate.ai.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "itinerary_days",
    foreignKeys = [
        ForeignKey(
            entity = TripEntity::class,
            parentColumns = ["id"],
            childColumns = ["tripId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [androidx.room.Index(value = ["tripId"])]
)
data class ItineraryDayEntity(
    @PrimaryKey(autoGenerate = true)
    val dayId: Long = 0,
    val tripId: Long,
    val dayNumber: Int,
    val description: String
)
