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
    companion object{

        val OPEN_AI_KEY = "zMh+tXv9bAq+oXG9ChSDWgNDYJFmgJNhQ6OgtNnOimVwXQ2pqUw2XrrD83sFTlGIPKehgVr42uZYcwJ+a46V4pnsn4wRBceMiM/FbJ6c+l9LowIcswXWFNjNeXKKkCiYz+HDtTv0qpxD"        //  const val FCM_BASE_URL = "https://fcm.googleapis.com/fcm/send"

    }


    @Headers(
        "Content-Type: application/json"

    )

    @POST("chat/completions")
    suspend fun chatCompletion(
        @Header("Authorization") apiKey: String,
        @Body request: ChatRequest
    ): Response<ChatResponse>


}