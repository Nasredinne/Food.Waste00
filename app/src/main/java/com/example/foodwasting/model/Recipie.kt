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

@Serializable
data class Recipe(
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val instructions: List<String>
)