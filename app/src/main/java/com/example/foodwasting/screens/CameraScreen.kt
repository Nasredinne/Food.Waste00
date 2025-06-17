@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.foodwasting.screens

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.foodwasting.MainActivity
import com.example.foodwasting.classification.TFLiteModel
import com.example.foodwasting.classification.centerCrop
import com.example.foodwasting.classification.classify
import com.example.foodwasting.ui.theme.lightgreen


@SuppressLint("ContextCastToActivity")
@Composable
fun CameraScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val scaffoldState = rememberBottomSheetScaffoldState()

    /*  BottomSheetScaffold(
          scaffoldState = scaffoldState,
          sheetPeekHeight = 0.dp,
          sheetContent = {}
      ) {
          padding ->


      } */
    var result by remember { mutableStateOf<FloatArray?>(null) }
    var classificationResult = remember {
        mutableStateOf<String>("")
    }

    val context = LocalContext.current as MainActivity
    var imageBitted = remember {
        mutableStateOf<Bitmap?>(null)
    }

    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE or
                        CameraController.VIDEO_CAPTURE
            )
        }
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    // Ensure the controller is bound ONCE when Composable is composed
    LaunchedEffect(controller) {
        controller.bindToLifecycle(lifecycleOwner)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
    ) {
        if (imageBitted.value != null) {
            /*Image(
                bitmap = imageBitted.value!!.asImageBitmap(),
                contentDescription = "Captured Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
            Text(
                "Top result value: size ${result?.size} => ${classificationResult.value}",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 30.dp)
            )*/

        } else {
            CameraPreview(
                controller = controller,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .border(border = BorderStroke(1.dp , color = lightgreen))
                    .align(Alignment.Center)
            )
        }
        /*
        val boxSizeInPx = 200f

        // Convert pixels to dp inside a composable
        val boxSizeInDp = with(LocalDensity.current) { boxSizeInPx.toDp() }

        */

        
        FloatingActionButton(
            onClick = {
                controller.takePicture(
                    ContextCompat.getMainExecutor(context),
                    object : OnImageCapturedCallback() {
                        override fun onCaptureSuccess(image: ImageProxy) {
                            try {
                                val matrix = Matrix().apply {
                                    postRotate(image.imageInfo.rotationDegrees.toFloat())
                                }
                                val rotatedBitmap = Bitmap.createBitmap(
                                    image.toBitmap(),
                                    0,
                                    0,
                                    image.width,
                                    image.height,
                                    matrix,
                                    true
                                )

                                val cropedImage = rotatedBitmap.centerCrop(500, 500)

                                imageBitted.value = cropedImage

                                // Run model inference
                                val tfLiteModel = TFLiteModel(context)
                                result = tfLiteModel.runModel(cropedImage)

                                Log.d("CameraScreen", "Model run successfully ${result?.joinToString("||")}")
                                classificationResult.value = classify(result!!)
                            } catch (e: Exception) {
                                Log.e("CameraScreen", "Error during model run: ${e.message}")
                                e.printStackTrace()
                            } finally {
                                image.close()
                            }
                        }

                        override fun onError(exception: ImageCaptureException) {
                            Log.e("CameraScreen", "Capture error: ${exception.message}")
                            exception.printStackTrace()
                        }
                    }
                )
            },
            modifier = Modifier
                .padding(100.dp)
                .align(Alignment.BottomCenter)
        ) {}
    }
}

@SuppressLint("ContextCastToActivity")
@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    modifier: Modifier
) {


    AndroidView(
        factory = { ctx ->
            PreviewView(ctx).apply {
                this.controller = controller

            }
            /*  val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
              cameraProviderFuture.addListener({
                  val cameraProvider = cameraProviderFuture.get()
                  val preview = Preview.Builder().build().also {
                      it.setSurfaceProvider(previewView.surfaceProvider)
                  }
                  val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                  try {
                      cameraProvider.unbindAll()
                      cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview)
                  } catch (exc: Exception) {
                      Log.e("CameraPreview", "Use case binding failed", exc)
                  }


                  //cameraProvider.
              }, ContextCompat.getMainExecutor(ctx)) */
            //  previewView
        },
        modifier = modifier


    )
}