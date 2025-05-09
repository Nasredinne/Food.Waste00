package com.example.foodwasting

import android.os.Bundle
import androidx.navigation.compose.NavHost
import android.webkit.WebSettings.TextSize
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.*
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodwasting.screens.MainScreen
import com.example.foodwasting.screens.SettingScreen
import com.example.foodwasting.ui.theme.FoodWastingTheme
import com.example.foodwasting.ui.theme.darkGreenT
import com.example.foodwasting.ui.theme.displayFontFamily
import com.example.foodwasting.ui.theme.fontAladin
import com.example.foodwasting.ui.theme.lightgreen
import com.example.foodwasting.ui.theme.onPrimaryLight
import com.example.foodwasting.utils.Routes

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            FoodWastingTheme {



                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Routes.mainScreen.route
                    ) {
                        composable(Routes.mainScreen.route) {
                            MainScreen(navController)
                        }
                        composable(Routes.settingsScreen.route) {
                            SettingScreen(navController)
                        }

                    }


          /*      Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(onPrimaryLight),
                    topBar = {
                        TopAppBar(
                            title = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                        contentDescription = "Back",
                                        modifier = Modifier
                                            .size(40.dp)
                                            .align(Alignment.CenterVertically)
                                    )
                                    Text(
                                        "Home",
                                        color = darkGreenT,
                                        fontSize = 32.sp,
                                        fontFamily = fontAladin,
                                        textAlign = TextAlign.Center
                                    )
                                    Icon(
                                        imageVector = Icons.Filled.Menu,
                                        contentDescription = "List",
                                        modifier = Modifier
                                            .size(40.dp)
                                            .align(Alignment.CenterVertically)
                                    )

                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(lightgreen)
                        )
                    }
                )
                { innerPadding ->


                 */
            }
        }
    }
}


