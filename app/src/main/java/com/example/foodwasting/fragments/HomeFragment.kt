package com.example.foodwasting.fragments

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodwasting.R
import com.example.foodwasting.model.Recipie
import com.example.foodwasting.ui.FoodCard
import com.example.foodwasting.ui.categories
import com.example.foodwasting.ui.theme.fontAladin
import com.example.foodwasting.ui.theme.lightBackground
import com.example.foodwasting.ui.theme.lightgreen
import com.example.foodwasting.ui.theme.onPrimaryLight
import com.example.foodwasting.ui.theme.secondaryLightMediumContrast
import com.example.foodwasting.utils.Routes


@Composable
fun HomeFragment(navController: NavController) {
    val recipList = listOf(
        Recipie("Test", "Test Content"),
        Recipie("Test", "Test Content"),
        Recipie("Test", "Test Content"),
        Recipie("Test", "Test Content"),
        Recipie("Test", "Test Content"),
        Recipie("Test", "Test Content"),
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = lightBackground)

    ) {

        Image(
            painter = painterResource(R.drawable.img_fruit) ,
            contentDescription = "image" ,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .padding(horizontal = 16.dp)


        ) {


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    //   .background(Color.Red)
                    .weight(0.2f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,

                ) {

                var textFealdvaluew by remember {
                    mutableStateOf("")
                }

                Row {
                    for (i in 1..5) {
                        Card(
                            elevation = CardDefaults.elevatedCardElevation(
                                defaultElevation = 0.dp
                            ),
                            modifier = Modifier
                                .width(70.dp)
                                .height(35.dp)
                                .padding(3.dp)
                                // .clip(shape = RoundedCornerShape(20.dp))
                                .border(
                                    width = 1.dp,
                                    color = lightgreen,
                                    shape = RoundedCornerShape(20.dp)
                                ),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Transparent,
                                contentColor = lightgreen
                            ),


                            ) {
                            Text(
                                "tag", modifier = Modifier
                                    .padding(5.dp)
                                    .align(Alignment.CenterHorizontally),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = lightgreen
                            )
                        }
                    }
                }
                Spacer(
                    Modifier.padding(4.dp)
                )
                OutlinedTextField(
                    shape = RoundedCornerShape(12.dp),
                    value = textFealdvaluew,
                    placeholder = {
                        Text(
                            "Search",
                            fontSize = 11.sp,
                            color = Color.LightGray
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "search",
                            tint = lightgreen
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = lightgreen,
                        focusedIndicatorColor = lightgreen,
                        cursorColor = lightgreen
                    ),
                    onValueChange = { textFealdvaluew = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)


                )

            }
            /*   SearchBar(
                   modifier = Modifier
                       .fillMaxWidth(0.8f)
                       .align(Alignment.CenterHorizontally)
                       .size(60.dp)
                       .padding(8.dp)
                       .weight(0.2f)
               ) */
            FoodCard(
                text = "Meals",
                list = recipList,
                color = lightgreen,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(5.dp)
                    .background(lightBackground)
                    .weight(0.3f)

            )
            FoodCard(
                text = "Mix",
                list = recipList,
                color = secondaryLightMediumContrast,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(5.dp)
                    .weight(0.3f)
            )
            categories(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
                    .weight(0.3f)
            )


        }

        Button(
            onClick = { navController.navigate(Routes.cameraScreen.route) },
            colors = buttonColors(
                containerColor = lightgreen,
                contentColor = onPrimaryLight
            ),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .size(70.dp)
                .padding(bottom = 16.dp)
                .align(Alignment.BottomCenter)

        ) {
            Text(
                text = "Generate Recipie",
                style = TextStyle(fontFamily = fontAladin, fontSize = 20.sp)
            )
        }
    }

}


