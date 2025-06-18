package com.example.foodwasting.repository

import android.util.Log
import com.example.foodwasting.model.Content
import com.example.foodwasting.model.GeminiRequest
import com.example.foodwasting.model.Part
import com.example.foodwasting.model.Recipe
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

// In MainRepository.kt
@Singleton
class MainRepository @Inject constructor(
    private val api: JsonHandler,
    private val json: Json,
    private val firestore: FirebaseFirestore, // ⭐ Inject Firestore

) {
    private val recipeCollection = firestore.collection("recipes")

    fun getAllRecipes(): Flow<List<Recipe>> = callbackFlow {
        // This is a real-time listener. It will fire every time the data changes.
        val snapshotListener = recipeCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                // If there's an error, close the flow with it
                close(error)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                // Map the documents to a list of Recipe objects
                val recipes = snapshot.toObjects(Recipe::class.java)
                // Send the latest list to the flow
                trySend(recipes).isSuccess
            }
        }

        // This is called when the flow is cancelled (e.g., ViewModel is cleared)
        // It's crucial to remove the listener to prevent memory leaks.
        awaitClose {
            snapshotListener.remove()
        }
    }

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
            val request =
                GeminiRequest(contents = listOf(Content(parts = listOf(Part(text = prompt)))))
            val OPEN_AI_KEY =
                "AIzaSyAhwZljBXZ6Q0-APnIVuRgoojAhxeU5U7g"     //  const val FCM_BASE_URL = "https://fcm.googleapis.com/fcm/send"

            // Call the new API function, passing the key from BuildConfig
            val response = api.generateRecipe(OPEN_AI_KEY, request)

            if (response.isSuccessful) {
                // Make sure the response body is not null
                val geminiResponse = response.body() ?: throw IllegalStateException("Response body is null")

                // ⭐ STEP 1: Correctly declare the 'rawResponseString' variable first.
                val rawResponseString = geminiResponse.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text
                    ?: throw IllegalStateException("Could not find text in the API response")

                Log.d("RecipeJson", "Raw response from API: $rawResponseString")

                // ⭐ STEP 2: Use the correctly declared variable.
                // Ensure the function names 'indexOfFirst' and 'indexOfLast' are spelled correctly.
                val jsonStartIndex = rawResponseString.indexOfFirst { it == '{' }
                val jsonEndIndex = rawResponseString.indexOfLast { it == '}' }

                if (jsonStartIndex != -1 && jsonEndIndex != -1) {
                    // Extract the clean JSON string
                    val recipeJsonString = rawResponseString.substring(jsonStartIndex, jsonEndIndex + 1)
                    Log.d("RecipeJson", "Cleaned JSON for parsing: $recipeJsonString")

                    // Parse the clean string
                    val recipe = json.decodeFromString<Recipe>(recipeJsonString)

                    // Save the parsed recipe to Firestore
                    try {
                        firestore.collection("recipes").add(recipe).await()
                        Log.d("Firestore", "Recipe successfully saved!")
                    } catch (e: Exception) {
                        Log.e("Firestore", "Error saving recipe", e)
                    }

                    Result.success(recipe)
                } else {
                    // This handles cases where the AI gives a response without any JSON object.
                    throw IllegalStateException("Could not find a valid JSON object in the API response.")
                }

            } else {
                // This part handles HTTP errors like 400, 404, 500 etc.
                val errorMessage = response.errorBody()?.string() ?: "Unknown HTTP error"
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
    val OPEN_AI_KEY =
        "AIzaSyAhwZljBXZ6Q0-APnIVuRgoojAhxeU5U7g"     //  const val FCM_BASE_URL = "https://fcm.googleapis.com/fcm/send"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain.request().newBuilder().addHeader("Authorization", "Bearer $OPEN_AI_KEY").build()
        return chain.proceed(request)
    }
}
