package com.example.foodwasting.repository

import com.example.foodwasting.model.ChatRequest
import com.example.foodwasting.model.ChatResponse
import com.example.foodwasting.model.Recipe
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface JsonHandler {
    // No more companion object with the key needed!

    @POST("chat/completions")
    suspend fun chatCompletion(
        // The apiKey parameter is gone!
        @Body request: ChatRequest
    ): Response<ChatResponse>
}