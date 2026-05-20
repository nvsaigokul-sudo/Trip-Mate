package com.tripmate.ai.data.remote

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.gson.Gson
import com.tripmate.ai.BuildConfig
import com.tripmate.ai.data.model.TripPlanResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GenerativeAIService {
    
    // We initialize the model. Note that we will pass the API key from BuildConfig.
    private val generativeModel = GenerativeModel(
        modelName = "gemini-flash-latest",
        apiKey = BuildConfig.GEMINI_API_KEY
    )
    private val gson = Gson()

    suspend fun generateTripPlan(prompt: String): Result<TripPlanResponse> = withContext(Dispatchers.IO) {
        try {
            val systemInstruction = """
                You are a premium AI travel planner. Based on the user's prompt, generate a highly detailed day-wise itinerary.
                You MUST return ONLY valid JSON matching this exact structure, with no markdown formatting or backticks around it:
                {
                  "destination": "Name of the main destination",
                  "budget": "Estimated budget",
                  "durationDays": 3,
                  "itinerary": [
                    {
                      "day": 1,
                      "description": "Day theme or description",
                      "places": [
                        {
                          "name": "Exact Name of Place",
                          "description": "Short vivid description",
                          "latitude": 15.2993,
                          "longitude": 74.1240,
                          "timeHint": "09:00 AM",
                          "type": "Attraction" 
                        }
                      ]
                    }
                  ]
                }
                Make sure the coordinates (latitude, longitude) are as accurate as possible. Type can be Attraction, Food, or Viewpoint.
            """.trimIndent()

            val fullPrompt = "$systemInstruction\n\nUser Prompt: $prompt"
            
            val response = generativeModel.generateContent(
                content { text(fullPrompt) }
            )
            
            val responseText = response.text?.trim()?.removePrefix("```json")?.removeSuffix("```")?.trim()
                ?: throw Exception("Empty response from AI")
                
            val tripPlan = gson.fromJson(responseText, TripPlanResponse::class.java)
            Result.success(tripPlan)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
