package com.example.foodwasting.repository

import android.util.Log
import com.example.foodwasting.model.Content
import com.example.foodwasting.model.GeminiRequest
import com.example.foodwasting.model.Part
import com.example.foodwasting.model.Recipe
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

// In MainRepository.kt
@Singleton
class MainRepository @Inject constructor(
    private val api: JsonHandler,
    private val json: Json
) {
    suspend fun makeRequest(foodName: String): Result<Recipe> {
        return try {
            val prompt = """
                 Provide a creative way to use $foodName to avoid food waste.
                 Respond ONLY with a valid JSON object that strictly follows this structure, with no extra text or markdown:
                 {
                   "title": "string",
                   "description": "string",
                   "ingredients": ["string", "string"],
                   "instructions": ["string", "string"]
                 }
             """.trimIndent()

            // Construct the Gemini-specific request
            val request = GeminiRequest(contents = listOf(Content(parts = listOf(Part(text = prompt)))))
            val OPEN_AI_KEY = "AIzaSyAhwZljBXZ6Q0-APnIVuRgoojAhxeU5U7g"     //  const val FCM_BASE_URL = "https://fcm.googleapis.com/fcm/send"

            // Call the new API function, passing the key from BuildConfig
            val response = api.generateRecipe(OPEN_AI_KEY, request)

            if (response.isSuccessful) {
                val geminiResponse = response.body()!!
                // The recipe JSON is inside a nested structure
                val recipeJsonString = geminiResponse.candidates.first().content.parts.first().text
                Log.d("RecipeJson", "Raw JSON from API: $recipeJsonString")

                // The rest is the same!
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
    val OPEN_AI_KEY = "AIzaSyAhwZljBXZ6Q0-APnIVuRgoojAhxeU5U7g"     //  const val FCM_BASE_URL = "https://fcm.googleapis.com/fcm/send"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $OPEN_AI_KEY")
            .build()
        return chain.proceed(request)
    }
}
