package com.example.foodwasting.classification

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer

class TFLiteModel(context: Context) {
    private val interpreter: Interpreter
    private val inputSize = 224
    private val numClasses = 18

    // ⭐ 1. DEFINE THE IMAGE PROCESSOR ⭐
    // This object defines all your preprocessing steps.
    private val imageProcessor = ImageProcessor.Builder()
        // Step 1: Resize the image to the model's input size.
        .add(ResizeOp(inputSize, inputSize, ResizeOp.ResizeMethod.BILINEAR))
        // Step 2: Normalize the pixel values from [0, 255] to [0, 1] for the float model.
        .add(NormalizeOp(0.0f, 255.0f))
        .build()

    init {
        try {
            val model: MappedByteBuffer = FileUtil.loadMappedFile(context, "model.tflite")
            val options = Interpreter.Options().apply {
                setNumThreads(4)
                setUseNNAPI(false)
            }
            interpreter = Interpreter(model, options)
        } catch (e: IOException) {
            throw IllegalStateException("Failed to initialize TFLite model: ${e.message}", e)
        }
    }

    fun runModel(bitmap: Bitmap): FloatArray {
        // ⭐ 2. CREATE A TENSORIMAGE AND PROCESS IT ⭐
        // No more manual scaling or buffer manipulation needed!
        var tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(bitmap)
        tensorImage = imageProcessor.process(tensorImage)

        // Prepare output buffer (this part stays the same)
        val output = Array(1) { FloatArray(numClasses) }

        // Run inference
        try {
            interpreter.run(tensorImage.buffer, output)
        } catch (e: Exception) {
            throw RuntimeException("Error during image classification: ${e.message}", e)
        }

        return output[0]
    }

    // close() method is still good and necessary.
    fun close() {
        interpreter.close()
    }
}