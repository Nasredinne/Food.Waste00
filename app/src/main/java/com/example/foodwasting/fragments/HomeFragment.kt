package com.example.foodwasting.fragments


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.foodwasting.R
import com.example.foodwasting.model.Recipe
import com.example.foodwasting.ui.FoodCard
import com.example.foodwasting.ui.theme.fontAladin
import com.example.foodwasting.ui.theme.lightBackground
import com.example.foodwasting.ui.theme.lightgreen
import com.example.foodwasting.ui.theme.onPrimaryLight
import com.example.foodwasting.ui.theme.secondaryLightMediumContrast
import com.example.foodwasting.ui.theme.white
import com.example.foodwasting.utils.Routes
import com.example.foodwasting.viewmodel.MainViewModel

@Composable
fun HomeFragment(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val savedRecipes by viewModel.savedRecipes.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = lightBackground)
    ) {
        Image(
            painter = painterResource(R.drawable.img_fruit),
            contentDescription = "image",
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
                    .weight(0.2f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                var textFealdvaluew by remember {
                    mutableStateOf("")
                }
                Box(
                    modifier = Modifier
                        .padding( vertical = 8.dp)
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(20.dp),
                            ambientColor = Color.Black.copy(alpha = 0.5f),
                            spotColor = Color.Black.copy(alpha = 0.5f)
                        )
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    white,
                                    white.copy(alpha = 0.95f)
                                )
                            ),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .clip(RoundedCornerShape(20.dp))
                        .padding(12.dp)
                ) {
                    OutlinedTextField(
                        value = textFealdvaluew,
                        onValueChange = { textFealdvaluew = it },
                        placeholder = {
                            Text(
                                text = "Search...",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "search",
                                tint = lightgreen
                            )
                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = lightgreen,
                            unfocusedIndicatorColor = lightgreen,
                            cursorColor = lightgreen
                        )
                    )
                }


            }

            FoodCard(
                text = "Meals",
                list = savedRecipes,
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
                list = savedRecipes,
                color = secondaryLightMediumContrast,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(5.dp)
                    .weight(0.3f)
            )
            /*Categories(
                /* modifier = Modifier
                     .fillMaxSize()
                     .padding(top = 10.dp)
                     .weight(0.3f)*/
            )*/
        }

        Button(
            onClick = { navController.navigate(Routes.cameraScreen.route)},
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

    @Composable
    fun HomeFragment2(
        savedRecipes: List<Recipe>,
    ) {
        /*Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = lightBackground)
        ) {
            Image(
                painter = painterResource(R.drawable.img_fruit),
                contentDescription = "image",
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
                        .weight(0.2f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    var textFealdvaluew by remember {
                        mutableStateOf("")
                    }
                    Box(
                        modifier = Modifier
                            .clip(shape = MaterialTheme.shapes.large)
                            .background(white)
                            .padding(12.dp)
                    ) {
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
                }

                FoodCard(
                    text = "Meals",
                    list = savedRecipes,
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
                    list = savedRecipes,
                    color = secondaryLightMediumContrast,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(5.dp)
                        .weight(0.3f)
                )
                /*Categories(
                    /* modifier = Modifier
                         .fillMaxSize()
                         .padding(top = 10.dp)
                         .weight(0.3f)*/
                )*/
            }

            Button(
                onClick = { navController.navigate(Routes.cameraScreen.route)},
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
        }*/
    }
