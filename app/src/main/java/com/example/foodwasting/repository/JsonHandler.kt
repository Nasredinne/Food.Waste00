package com.example.foodwasting.repository

import com.example.foodwasting.model.GeminiRequest
import com.example.foodwasting.model.GeminiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

// In JsonHandler.kt
interface JsonHandler {
    @POST("v1beta/models/gemini-1.5-flash-latest:generateContent")
    suspend fun generateRecipe(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): Response<GeminiResponse>
}