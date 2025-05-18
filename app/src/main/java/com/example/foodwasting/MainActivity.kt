package com.example.foodwasting

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.navigation.compose.NavHost
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.camera.view.LifecycleCameraController
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodwasting.screens.CameraScreen
import com.example.foodwasting.screens.MainScreen
import com.example.foodwasting.ui.theme.FoodWastingTheme
import com.example.foodwasting.utils.Routes

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this, CAMERA_PERMISSION, 0
            )
        }
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
                    composable(Routes.cameraScreen.route) {
                        CameraScreen(navController)
                    }


                }
            }

        }
    }

        private fun hasRequiredPermissions(): Boolean {
            return CAMERA_PERMISSION.all {
                ContextCompat.checkSelfPermission(
                    applicationContext,
                    it
                ) == PackageManager.PERMISSION_GRANTED
            }
        }

        companion object {
            private val CAMERA_PERMISSION = arrayOf(
                Manifest.permission.CAMERA,
            )
        }
    }


