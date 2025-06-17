package com.example.foodwasting.repository

import android.util.Log
import com.example.foodwasting.model.ChatMessage
import com.example.foodwasting.model.ChatRequest
import com.example.foodwasting.model.Recipe
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val api: JsonHandler,
    private val json: Json
) {
    suspend fun makeRequest(foodName: String): Result<Recipe> {
        return try {
            val request = ChatRequest(
                model = "gpt-4-turbo", // A model that officially supports JSON mode
                messages = listOf(
                    // A system message helps guide the AI's behavior
                    ChatMessage(
                        role = "system",
                        content = "You are a helpful assistant designed to output JSON."
                    ),
                    ChatMessage(
                        role = "user",
                        content = """
                            Provide a creative way to use $foodName to avoid food waste.
                            Respond with a JSON object that strictly follows this structure:
                            {
                              "title": "string",
                              "description": "string",
                              "ingredients": ["string", "string"],
                              "instructions": ["string", "string"]
                            }
                        """.trimIndent()
                    )
                ),
                // ⭐ NOW YOU CAN USE THE NEW FIELD ⭐
                responseFormat = mapOf("type" to "json_object")
            )

            val response = api.chatCompletion(request)

            Log.d("NetworkCheck", "Response code: ${response.code()}")

            if (response.isSuccessful) {
                val chatResponse = response.body() ?: throw IllegalStateException("Response body is null")
                val recipeJsonString = chatResponse.choices.firstOrNull()?.message?.content
                    ?: throw IllegalStateException("No content found in response")

                Log.d("RecipeJson", "Raw JSON from API: $recipeJsonString")

                // With JSON mode, the string should be perfect. No more cleaning needed!
                val recipe = json.decodeFromString<Recipe>(recipeJsonString)
                Result.success(recipe)

            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                Log.e("HTTP Error", errorMessage)
                Result.failure(Exception("HTTP Error: $errorMessage"))
            }
        } catch (e: Exception) {
            Log.e("Unhandled Exception", e.stackTraceToString())
            Result.failure(e)
        }
    }
}


@Singleton
class AuthInterceptor @Inject constructor() : Interceptor {
    val OPEN_AI_KEY = "zMh+tXv9bAq+oXG9ChSDWgNDYJFmgJNhQ6OgtNnOimVwXQ2pqUw2XrrD83sFTlGIPKehgVr42uZYcwJ+a46V4pnsn4wRBceMiM/FbJ6c+l9LowIcswXWFNjNeXKKkCiYz+HDtTv0qpxD"        //  const val FCM_BASE_URL = "https://fcm.googleapis.com/fcm/send"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $OPEN_AI_KEY")
            .build()
        return chain.proceed(request)
    }
}
