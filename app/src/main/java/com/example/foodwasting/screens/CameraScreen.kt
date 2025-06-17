@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.foodwasting.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.foodwasting.ui.theme.lightgreen
import com.example.foodwasting.viewmodel.MainViewModel
import com.example.foodwasting.R
import com.example.foodwasting.viewmodel.RecipeState

@SuppressLint("MissingPermission") // Ensure you handle camera permissions before navigating here
@Composable
fun CameraScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel() // Get ViewModel instance via Hilt
) {
    val scaffoldState = rememberBottomSheetScaffoldState()

    // Observe the state from the ViewModel
    val recipeState by viewModel.recipeState.collectAsState()

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
        }
    }
    LaunchedEffect(Unit) {
        controller.bindToLifecycle(lifecycleOwner)
    }

    // Automatically expand the bottom sheet when the state is not Idle
    LaunchedEffect(recipeState) {
        if (recipeState !is RecipeState.Idle) {
            scaffoldState.bottomSheetState.expand()
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp, // We control the sheet programmatically
        sheetContent = {
            // The content of the bottom sheet is determined by the ViewModel's state
            RecipeSheetContent(state = recipeState)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AndroidView(
                factory = { ctx -> PreviewView(ctx).apply { this.controller = controller } },
                modifier = Modifier.fillMaxSize()
            )

            // Bounding box overlay
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .border(border = BorderStroke(2.dp, color = lightgreen))
                    .align(Alignment.Center)
            )

            // Capture Button
            FloatingActionButton(
                onClick = {
                    // When clicking, we only care about getting the ImageProxy.
                    // The ViewModel handles the rest.
                    controller.takePicture(
                        ContextCompat.getMainExecutor(context),
                        object : OnImageCapturedCallback() {
                            override fun onCaptureSuccess(image: ImageProxy) {
                                viewModel.processImageAndFetchRecipe(image)
                            }

                            override fun onError(exception: ImageCaptureException) {
                                Log.e("CameraScreen", "Capture error: ", exception)
                                // Optionally show a snackbar or toast for capture errors
                            }
                        }
                    )
                },
                modifier = Modifier
                    .padding(bottom = 48.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Icon(painter = painterResource(R.drawable.camera), contentDescription = "Take Picture")
            }
        }
    }
}

@Composable
fun RecipeSheetContent(state: RecipeState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 250.dp) // Give the sheet a nice default size
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is RecipeState.Idle -> {
                // Can be empty or show a prompt
                Text("Take a picture of a food item to get a recipe!")
            }
            is RecipeState.Loading -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(Modifier.height(16.dp))
                    Text("Finding a creative recipe...")
                }
            }
            is RecipeState.Error -> {
                Text(
                    text = "Error: ${state.message}",
                    color = MaterialTheme.colorScheme.error
                )
            }
            is RecipeState.Success -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(state.recipe.title, style = MaterialTheme.typography.headlineMedium)
                    Spacer(Modifier.height(8.dp))
                    Text(state.recipe.description, style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.height(16.dp))
                    Text("Ingredients:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    state.recipe.ingredients.forEach { ingredient ->
                        Text("• $ingredient")
                    }
                    // Add instructions similarly if needed
                }
            }
        }
    }
}