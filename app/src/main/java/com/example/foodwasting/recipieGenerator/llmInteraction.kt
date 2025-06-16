package com.example.foodwasting.recipieGenerator

import android.util.Log
import com.example.foodwasting.model.OPEN_AI_KEY
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.IOException

@Serializable
data class ChatMessage(
    val role: String,
    val content: String
)

@Serializable
data class ChatRequest(
    val model: String,
    val messages: List<ChatMessage>
)

@Serializable
data class ChatChoice(
    val index: Int,
    val message: ChatMessage
)

@Serializable
data class ChatResponse(
    val choices: List<ChatChoice>
)



suspend fun fetchRecipe(
    apiKey: String = OPEN_AI_KEY,
    apiHost: String = "https://api.llm7.io/v1",
    model: String = "gpt-4.1-nano",
    timeoutMillis: Long = 30_000
): Recipe = withContext(Dispatchers.IO) {

    val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
        install(HttpTimeout) {

            requestTimeoutMillis = timeoutMillis
        }
    }
    println("Timeout plugin installed: ${client.pluginOrNull(HttpTimeout) != null}")


    try {
        Log.d("LLM", "Sending chat request")

        val response: ChatResponse = client.post("$apiHost/chat/completions") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $apiKey")
            setBody(
                ChatRequest(
                    model = model,
                    messages = listOf(
                        ChatMessage(
                            role = "user",
                            content = """
                            Provide a recipe in JSON format with the following fields:
                            - title: string (the name of the dish)
                            - description: string (a brief description of the dish)
                            - recipe: string (step-by-step cooking instructions)
                            Ensure the response is valid JSON.
                        """.trimIndent()
                        )
                    )
                )
            )
        }.body<ChatResponse>()

        val jsonText = response.choices.firstOrNull()?.message?.content
            ?: throw IOException("Empty or invalid response from API")

        return@withContext Json { ignoreUnknownKeys = true }
            .decodeFromString<Recipe>(jsonText)

    } catch (e: Exception) {
        Log.e("LLM", "Error: ${e.message}", e)
        throw IOException("Failed to fetch recipe: ${e.message}", e)
    } finally {
        client.close()
    }
}
