package com.example.foodwasting.utils

sealed class Routes(val route: String) {
    object mainScreen : Routes("mainScreen")
    object settingsScreen : Routes("settingsScreen")
}