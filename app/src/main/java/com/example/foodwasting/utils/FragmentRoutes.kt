package com.example.foodwasting.utils

import androidx.annotation.DrawableRes
import com.example.foodwasting.R

sealed class FragmentRoutes(
    var title:String,
    var route:String,
    @DrawableRes var icon:Int
) {
    object Home : FragmentRoutes("Home", "home", R.drawable.ic_home)
    object Profile : FragmentRoutes("Profile", "profile", R.drawable.ic_person)
    object Settings : FragmentRoutes("Settings", "settings", R.drawable.ic_home)

}