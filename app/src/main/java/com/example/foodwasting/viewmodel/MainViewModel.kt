package com.example.foodwasting.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodwasting.classification.TFLiteModel
import com.example.foodwasting.classification.centerCrop
import com.example.foodwasting.classification.classify
import com.example.foodwasting.classification.getTopClassificationLabel
import com.example.foodwasting.model.Recipe
import com.example.foodwasting.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// This sealed class definition is correct and well-structured.
sealed class RecipeState {
    data object Idle : RecipeState()
    data object Loading : RecipeState()
    data class Success(val recipe: Recipe) : RecipeState()
    data class Error(val message: String) : RecipeState()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val tfLiteModel: TFLiteModel,
    @ApplicationContext private val applicationContext: Context
) : ViewModel() {

    private val _recipeState = MutableStateFlow<RecipeState>(RecipeState.Idle)
    val recipeState = _recipeState.asStateFlow()

    // ⭐ THIS IS THE NEW FUNCTION YOU NEED TO ADD ⭐
    /**
     * Fetches a recipe directly from a food name string.
     * This is used by RecipeScreen.
     */
    fun makeRequest(foodName: String) {
        viewModelScope.launch {
            _recipeState.value = RecipeState.Loading
            Log.d("ViewModel", "Requesting recipe for: $foodName")
            repository.makeRequest(foodName)
                .onSuccess { recipe ->
                    _recipeState.value = RecipeState.Success(recipe)
                    Log.d("ViewModel", "Successfully fetched recipe for '$foodName'")
                }
                .onFailure { exception ->
                    val errorMessage = exception.message ?: "An unknown error occurred"
                    _recipeState.value = RecipeState.Error(errorMessage)
                    Log.e("ViewModel", "Error fetching recipe for '$foodName'", exception)
                }
        }
    }

    /**
     * Processes a camera image, classifies it, and then fetches a recipe.
     * This is used by CameraScreen.
     */
// In MainViewModel.kt

    fun processImageAndFetchRecipe(image: ImageProxy) {
        viewModelScope.launch {
            _recipeState.value = RecipeState.Loading
            try {
                val rotatedBitmap = image.toBitmap().rotate(image.imageInfo.rotationDegrees.toFloat())
                // The crop size should match your model input size for consistency
                val croppedImage = rotatedBitmap.centerCrop(224, 224)

                // ⭐ REUSE THE INJECTED INSTANCE - MUCH FASTER! ⭐
                val classificationScores = tfLiteModel.runModel(croppedImage)

                val topFoodName = getTopClassificationLabel(classificationScores)
                Log.d("ViewModel", "Classified food: $topFoodName")
                makeRequest(topFoodName)

            } catch (e: Exception) {
                Log.e("ViewModel", "An error occurred during image processing", e)
                _recipeState.value = RecipeState.Error("Failed to process image.")
            } finally {
                image.close()
            }
        }
    }
    /**
     * A helper function to reset the state, e.g., when the user dismisses a bottom sheet.
     */
    fun resetState() {
        _recipeState.value = RecipeState.Idle
    }
    override fun onCleared() {
        super.onCleared()
        tfLiteModel.close()
    }

}

/**
 * Helper extension function for rotating a bitmap.
 */
private fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}