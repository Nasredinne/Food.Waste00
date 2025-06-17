package com.example.foodwasting.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodwasting.model.Recipe
import com.example.foodwasting.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Result

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {
    var recipeState by mutableStateOf<RecipeState>(RecipeState.Idle)
        private set

    fun makeRequest(foodName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            recipeState = RecipeState.Loading
            val result = repository.makeRequest(foodName)
            if (result.isSuccess) {
                recipeState = RecipeState.Success(result.getOrNull())
            } else {
                recipeState = RecipeState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }

        }
    }
}



sealed class RecipeState {
    object Idle : RecipeState()
    object Loading : RecipeState()
    data class Success(val recipe: Recipe?) : RecipeState()
    data class Error(val message: String) : RecipeState()
}