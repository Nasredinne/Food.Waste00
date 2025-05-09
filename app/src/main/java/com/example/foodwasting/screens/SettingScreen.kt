package com.example.foodwasting.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SettingScreen(navController: NavController, modifier: Modifier = Modifier) {
    Box (modifier = Modifier
        .fillMaxSize()
        .background(Color.Green)){
        Text("SETTING SCREEN +_+" , color = Color.White)
    }
}

@Preview
@Composable
private fun stting() {
    val navController = rememberNavController()
    SettingScreen(navController)
}