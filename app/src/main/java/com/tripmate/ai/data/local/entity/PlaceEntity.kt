package com.tripmate.ai.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "places",
    foreignKeys = [
        ForeignKey(
            entity = ItineraryDayEntity::class,
            parentColumns = ["dayId"],
            childColumns = ["dayId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [androidx.room.Index(value = ["dayId"])]
)
data class PlaceEntity(
    @PrimaryKey(autoGenerate = true)
    val placeId: Long = 0,
    val dayId: Long,
    val name: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val timeHint: String, // e.g. "09:00 AM" or "Morning"
    val type: String // "Attraction", "Food", "Viewpoint"
)
