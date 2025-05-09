package com.example.foodwasting.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.foodwasting.fragments.HomeFragment
import com.example.foodwasting.fragments.ProfileFragment
import com.example.foodwasting.fragments.SettingsFragment


@Composable
fun BottomNavHost(navController: NavHostController){



    NavHost (
        navController=navController,
        startDestination = FragmentRoutes.Home.route
    ){
        composable(FragmentRoutes.Home.route){
            HomeFragment()
        }
        composable(FragmentRoutes.Profile.route) {
            ProfileFragment()
        }
        composable(FragmentRoutes.Settings.route) {
            SettingsFragment()
        }
    }

}