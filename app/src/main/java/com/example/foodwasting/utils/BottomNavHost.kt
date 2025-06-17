package com.example.foodwasting.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.foodwasting.fragments.HomeFragment
import com.example.foodwasting.fragments.ProfileFragment
import com.example.foodwasting.fragments.SettingsFragment


@Composable
fun BottomNavHost(navController: NavHostController, navCamController: NavController){

    NavHost (
        navController=navController,
        startDestination = FragmentRoutes.Home.route
    ){
        composable(FragmentRoutes.Home.route){
            HomeFragment(navCamController)
        }
        composable(FragmentRoutes.Profile.route) {
            ProfileFragment()
        }
        composable(FragmentRoutes.Settings.route) {
            SettingsFragment()
        }

    }

}

