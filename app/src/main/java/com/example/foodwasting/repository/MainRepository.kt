package com.example.foodwasting.repository

import android.util.Log
import com.example.foodwasting.model.ChatMessage
import com.example.foodwasting.model.ChatRequest
import com.example.foodwasting.model.Recipe
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.serialization.json.Json
import javax.inject.Inject

@ActivityScoped
class MainRepository @Inject constructor(
    private val api: JsonHandler
) {
    suspend fun makeRequest(foodName: String): Result<Recipe> {
        return try {
            val request = ChatRequest(
                model = "gpt-4.1-nano",
                messages = listOf(
                    ChatMessage(
                        role = "user",
                        content = """
                            Provide a creative way to use $foodName to avoid food waste in JSON format with the following fields:
                            - title
                            - description
                            - ingredients
                            - instructions
                        """.trimIndent()
                    )
                )
            )

            val response = api.chatCompletion(
                apiKey = "Bearer ${JsonHandler.OPEN_AI_KEY}",
                request = request
            )

            Log.d("NetworkCheck", "Response code: ${response.code()}")

            if (response.isSuccessful) {
                val json = Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    encodeDefaults = true
                }

                val chatResponse = response.body() ?: throw IllegalStateException("Response body is null")
                Log.d("NetworkCheck", "Response body: $chatResponse")

                val recipeJsonString = chatResponse.choices.firstOrNull()?.message?.content
                    ?: throw IllegalStateException("No content found in response")

                val cleanedJsonString = recipeJsonString
                    .replace("```json", "")
                    .replace("```", "")
                    .trim()

                Log.d("RecipeJson", cleanedJsonString)

                val recipe = json.decodeFromString<Recipe>(cleanedJsonString)
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