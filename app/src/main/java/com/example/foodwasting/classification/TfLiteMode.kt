package com.example.foodwasting.classification

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer

class TFLiteModel(context: Context) {
    private var interpreter: Interpreter? = null
    private val inputSize = 224 // Model input size (adjust based on your model)
    private val numClasses = 18 // Number of output classes (adjust based on your model)
    private val pixelSize = 3 // RGB channels
    private val inputImageType =
        Float::class.java  //Adjust if your model uses different input type (e.g., Byte)

    init {
        try {
            // Load the model from assets
            val model: MappedByteBuffer = FileUtil.loadMappedFile(context, "model.tflite")
            val options = Interpreter.Options().apply {
                setNumThreads(4)
                setUseNNAPI(false)
            }
            interpreter = Interpreter(model, options)
        } catch (e: Exception) {
            throw IllegalStateException("Failed to initialize TFLite model: ${e.message}", e)
        }
    }

    fun runModel(bitmap: Bitmap): FloatArray {
        interpreter ?: throw IllegalStateException("Interpreter not initialized")

        try {
            // Preprocess the image
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, inputSize, inputSize, true)
            val inputBuffer = preprocessImage(scaledBitmap)

            // Prepare output buffer
            val output = Array(1) { FloatArray(numClasses) }

            // Run inference
            interpreter?.run(inputBuffer, output)
                ?: throw IllegalStateException("Interpreter is null during inference")

            return output[0]
        } catch (e: Exception) {
            throw RuntimeException("Error during image classification: ${e.message}", e)
        }
    }


   private fun preprocessImage(bitmap: Bitmap): ByteBuffer {
        // Allocate ByteBuffer for input (float32, 4 bytes per value)
        val buffer = ByteBuffer.allocateDirect(
            1 * inputSize * inputSize * pixelSize * 4 // 4 bytes for float32
        )
        buffer.order(ByteOrder.nativeOrder())
        val pixels = IntArray(inputSize * inputSize)
        bitmap.getPixels(pixels, 0, inputSize, 0, 0, inputSize, inputSize)

        // Normalize pixel values to [0, 1] for float32 input
        for (pixel in pixels) {
            buffer.putFloat(((pixel shr 16) and 0xFF) / 255.0f) // Red
            buffer.putFloat(((pixel shr 8) and 0xFF) / 255.0f)  // Green
            buffer.putFloat((pixel and 0xFF) / 255.0f)         // Blue
        }

        return buffer
    }



    fun close() {
        interpreter?.close()
        interpreter = null
    }
}