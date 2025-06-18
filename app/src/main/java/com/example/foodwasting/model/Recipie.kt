package com.example.foodwasting.model

import com.google.firebase.firestore.DocumentId
import kotlinx.serialization.Serializable


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
// @Entity(tableName = "recipes") // <-- DELETE THIS LINE
data class Recipe(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val ingredients: List<String> = emptyList(),
    val instructions: List<String> = emptyList()
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
