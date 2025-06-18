package com.example.foodwasting.model

import android.icu.text.CaseMap.Title
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Recipie(
    var title: String,
    var centent : String
)
@Serializable
data class ChatMessage(
    val role: String,
    val content: String
)



@Serializable
data class ChatChoice(
    val index: Int?,
    val message: ChatMessage
)



@Serializable
data class Recipe(


    val title: String = "",
    val description: String="",
    val ingredients: List<String> =emptyList(),
    val instructions: List<String> =emptyList()
)
    // Replace your old Chat... classes with these new Gemini... classes
@Serializable
data class GeminiRequest(val contents: List<Content>)

@Serializable
data class Content(val parts: List<Part>)

@Serializable
data class Part(val text: String)

@Serializable
data class GeminiResponse(val candidates: List<Candidate>)

@Serializable
data class Candidate(val content: Content)

// Your Recipe data class can stay exactly the same!
