package com.example.foodwasting.fragments

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.example.foodwasting.model.Recipe
import com.example.foodwasting.viewmodel.MainViewModel
import com.example.foodwasting.viewmodel.RecipeState
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

        var foodName = "rotten orange"
        Button(
            contentPadding = PaddingValues(2.dp),
            onClick = {

                //val recipeState = viewModel.recipeState
                
                /*if (recipeState is RecipeState.Success) {
                    recipe = recipeState.recipe!!
                    title.add(recipe.title)
                    description.add(recipe.description)
                    ingrediant.add(recipe.ingredients.toString())
                    instructions.add(recipe.instructions.toString())
                    Log.d("Recipe", recipe.toString())
                }

                 */

            }
        ) {
            Text("Test")
        }

       /* Column {
            Text("Title : ${title}")
            Text("Title : ${description}")
            Text("Title : ${ingrediant}")
            Text("Title : ${instructions}")
        }
*/

    }

}

