package com.example.foodwasting.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.foodwasting.ui.theme.darkGreenT
import com.example.foodwasting.ui.theme.fontAladin
import com.example.foodwasting.ui.theme.lightgreen
import com.example.foodwasting.ui.theme.onPrimaryLight
import com.example.foodwasting.utils.BottomNavHost
import com.example.foodwasting.utils.FragmentRoutes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController
) {
    val bottomNavController = rememberNavController()
    Scaffold(
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
                        Text(
                            "Home",
                            color = darkGreenT,
                            fontSize = 32.sp,
                            fontFamily = fontAladin,
                            textAlign = TextAlign.Center
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(lightgreen),
                navigationIcon = {
                    IconButton(onClick = { print("hi") }) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = "List",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavBar(bottomNavController , navController)
        }
    )
    { innerPadding ->

        Box(modifier = Modifier.padding(innerPadding)){
            BottomNavHost(navController = bottomNavController , navCamController = navController)
        }

    }
}



@Composable
fun BottomNavBar(
    navController: NavController,
    navCamController: NavController
) {
    val fragmentes = listOf(
        FragmentRoutes.Home,
        FragmentRoutes.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = lightgreen,
        modifier = Modifier.clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
    ) {
        fragmentes.forEach { fragment ->
            val isSelected = currentDestination?.route == fragment.route
            NavigationBarItem(
                selected = isSelected,
                onClick = { navController.navigate(fragment.route) },
                icon = {
                    Icon(
                        painter = painterResource(id = fragment.icon),
                        contentDescription = fragment.title,
                        modifier = Modifier.size(30.dp),
                       // tint = if (isSelected) Color.White else Color.Gray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
private fun Mainecreen(navController: NavController) {
    val navController = rememberNavController()
    MainScreen(navController = navController)
}