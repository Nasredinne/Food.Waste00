package com.example.foodwasting.recipieGenerator
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Recipe(
    val title: String,
    val description: String,
    val recipe: String
)
