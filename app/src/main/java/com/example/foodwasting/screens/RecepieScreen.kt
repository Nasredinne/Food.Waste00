package com.example.foodwasting

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodwasting.model.Recipe
import com.example.foodwasting.viewmodel.MainViewModel
import com.example.foodwasting.viewmodel.RecipeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    // In a real app, you would pass this from the previous screen
    foodName: String = "rotten apple",
    viewModel: MainViewModel = hiltViewModel()
) {
    // This will trigger the request once when the screen is first composed
    // or if the foodName ever changes.
    LaunchedEffect(foodName) {
        viewModel.makeRequest(foodName)
    }

    // ⭐ FIX #1: Collect the state from the StateFlow into a Compose State.
    // This ensures your UI recomposes when the state changes.
    val recipeState by viewModel.recipeState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        // ⭐ FIX #2: Use the local 'recipeState' variable.
                        // The smart cast now works because the compiler knows 'state' is stable.
                        text = when (val state = recipeState) {
                            is RecipeState.Success -> state.recipe.title.ifEmpty { "Recipe" }
                            is RecipeState.Error -> "Error"
                            is RecipeState.Idle -> "Recipe Finder"
                            is RecipeState.Loading -> "Loading Recipe..."
                        },
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            // ⭐ FIX #2 (continued): Create a stable local variable 'state' for the when block.
            when (val state = recipeState) {
                is RecipeState.Idle -> Text("No recipe requested")
                is RecipeState.Loading -> CircularProgressIndicator()
                // The smart cast to 'Success' now works perfectly.
                is RecipeState.Success -> RecipeContent(state.recipe)
                // The smart cast to 'Error' also works.
                is RecipeState.Error -> ErrorContent(
                    message = state.message,
                    // ⭐ FIX #3: Pass a lambda for the retry action.
                    onRetry = { viewModel.makeRequest(foodName) }
                )
            }
        }
    }
}

@Composable
fun RecipeContent(recipe: Recipe) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ... your RecipeContent code is perfect, no changes needed ...
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = recipe.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Ingredients",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    recipe.ingredients.forEach { ingredient ->
                        Text(
                            text = "• $ingredient",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Instructions",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    recipe.instructions.forEachIndexed { index, instruction ->
                        Text(
                            text = "${index + 1}. $instruction",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}


// ⭐ FIX #3 (continued): Change the signature to accept a lambda.
@Composable
fun ErrorContent(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Error: $message",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        // The onClick now calls the lambda, which resolves the 'makeRequest' reference.
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}