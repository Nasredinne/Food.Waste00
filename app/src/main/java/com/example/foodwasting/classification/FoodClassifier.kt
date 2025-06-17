package com.example.foodwasting.classification

import android.graphics.Bitmap
import com.example.foodwasting.model.Classification

interface FoodClassifier {
    fun classify(bitmap : Bitmap): Classification
}