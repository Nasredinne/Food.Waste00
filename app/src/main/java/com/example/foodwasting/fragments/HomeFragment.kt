package com.example.foodwasting.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.example.foodwasting.model.Recipie
import com.example.foodwasting.ui.FoodCard
import com.example.foodwasting.ui.SearchBar
import com.example.foodwasting.ui.categories
import com.example.foodwasting.ui.theme.fontAladin
import com.example.foodwasting.ui.theme.lightBackground
import com.example.foodwasting.ui.theme.lightgreen
import com.example.foodwasting.ui.theme.onPrimaryLight
import com.example.foodwasting.ui.theme.secondaryLightMediumContrast


@Preview
@Composable
fun HomeFragment() {

    val recipList = listOf(
        Recipie("Test", "Test Content"),
        Recipie("Test", "Test Content"),
        Recipie("Test", "Test Content"),
        Recipie("Test", "Test Content"),
        Recipie("Test", "Test Content"),
        Recipie("Test", "Test Content"),
    )
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = lightBackground)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = lightBackground)
        ) {
            Spacer(
                Modifier.padding(8.dp)
            )
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.CenterHorizontally)
                    .size(60.dp)
                    .padding(8.dp)
            )
            FoodCard(
                text = "Meals",
                list = recipList,
                color = lightgreen,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(10.dp)
                    .background(lightBackground)

            )
            FoodCard(
                text = "Mix",
                list = recipList,
                color = secondaryLightMediumContrast,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(10.dp)
            )
            categories(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
            )


        }

        Button(
            onClick = { println("hello")},
            colors = buttonColors(
                containerColor = lightgreen,
                contentColor = onPrimaryLight
            ),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .size(70.dp)
                .padding(bottom = 24.dp)
                .align(Alignment.BottomCenter)

        ){
            Text(text = "Generate Recipie" , style = TextStyle(fontFamily = fontAladin , fontSize = 20.sp))
        }
    }

}