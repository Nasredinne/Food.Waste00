package com.example.foodwasting.repository

import android.util.Log
import com.example.foodwasting.model.ChatMessage
import com.example.foodwasting.model.ChatRequest
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class MainRepository @Inject constructor(
    val api: JsonHandler,

    ) {

    suspend fun makeRequest() {
        try {
            val request = ChatRequest(
                model = "gpt-4.1-nano",
                messages = listOf(
                    ChatMessage(
                        role = "user",
                        content = """
                    Provide a recipe in JSON format with:
                    - title
                    - description
                    - recipe
                """.trimIndent()
                    )
                )
            )
            val response = api.chatCompletion(
                apiKey = "Bearer ${JsonHandler.OPEN_AI_KEY}",
                request = request
            )  // FIX: Change return type to Response<ChatResponse> or wrap in try
            if (response.isSuccessful) {
                val content = response.body()?.choices?.firstOrNull()?.message?.content
                Log.d("Success", "Recipe: $content")
            } else {
                Log.e("HTTP Error", response.errorBody()?.string() ?: "Unknown error")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("Unhandled Exception", e.stackTraceToString())  // ✅ print full cause
        }




    }
}
