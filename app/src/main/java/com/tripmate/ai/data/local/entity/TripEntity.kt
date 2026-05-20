package com.tripmate.ai.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val prompt: String,
    val title: String,
    val destination: String,
    val budget: String,
    val durationDays: Int,
    val createdAt: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false
)
