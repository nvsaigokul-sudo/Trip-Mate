package com.tripmate.ai.data.model

import com.google.gson.annotations.SerializedName

data class TripPlanResponse(
    @SerializedName("destination") val destination: String,
    @SerializedName("budget") val budget: String,
    @SerializedName("durationDays") val durationDays: Int,
    @SerializedName("itinerary") val itinerary: List<DayPlan>
)

data class DayPlan(
    @SerializedName("day") val day: Int,
    @SerializedName("description") val description: String,
    @SerializedName("places") val places: List<PlacePlan>
)

data class PlacePlan(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("timeHint") val timeHint: String,
    @SerializedName("type") val type: String
)
