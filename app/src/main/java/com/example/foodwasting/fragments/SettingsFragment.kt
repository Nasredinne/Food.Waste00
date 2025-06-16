package com.example.foodwasting.fragments

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodwasting.recipieGenerator.Recipe
import com.example.foodwasting.recipieGenerator.fetchRecipe
import com.example.foodwasting.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.IOException


@Preview(showBackground = true)
@Composable
fun SettingsFragment(
    viewModel:MainViewModel= hiltViewModel()
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        var recipe: Recipe
        var miw = mutableSetOf("")

        Button(
            contentPadding = PaddingValues(2.dp),
            onClick = {

                viewModel.makeRequest()
               /* println("Recipe: ")
                Log.e("SettingsFragment", "button clicked before runBlocking")
                scope.launch {
                    try {
                        Log.d("Recipe: ", "button clicked")
                        recipe = fetchRecipe()
                        Log.d("Recipe: ", "Title:  ${recipe.title}")
                        println("Description: ${recipe.description}")
                        println("Instructions: ${recipe.recipe}")
                        miw.add(recipe.title)
                    } catch (e: IOException) {
                        println("Error: ${e.message}")
                    }
                }


                Log.d("Recipe: ", "button clicked after runBlocking") */

            }
        ) {
            Text("Test")
        }
        Text("miw {$miw}", modifier = Modifier.align(Alignment.Center))

    }
}

