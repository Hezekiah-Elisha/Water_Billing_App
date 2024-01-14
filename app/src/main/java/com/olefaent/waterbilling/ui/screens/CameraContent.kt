package com.olefaent.waterbilling.ui.screens

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    modifier : Modifier = Modifier
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    AndroidView(
        factory = {
            PreviewView(it).apply{
                this.controller = controller
                controller.bindToLifecycle(lifeCycleOwner)
            }
        },
        modifier = modifier
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraContent(
    meterId: Int,
    navController: NavController,
    meterViewModel: MeterViewModel
){
//    val meterViewModel : MeterViewModel = viewModel(factory = MeterViewModel.Factory)

    val context = LocalContext.current
    val lifeCycleOwner = LocalLifecycleOwner.current
    val cameraCotroller = remember { LifecycleCameraController(context) }
    val imageBitmap = remember { mutableStateOf<Bitmap?>(null) }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                content = {
                    Text("Take picture")
                    Icon(imageVector = Icons.Default.Send, contentDescription = "Capture")
                },
                onClick = {
                    val mainExecutor = ContextCompat.getMainExecutor(context)


                    val outputDirectory = context.getExternalFilesDir(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString())
                    val photoFile = File(outputDirectory, "${System.currentTimeMillis()}.jpg")


                    val outputFileOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

                    cameraCotroller.takePicture(outputFileOptions, mainExecutor, object: ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                            // Handle the saved image here
                            val savedUri = outputFileResults.savedUri ?: Uri.fromFile(photoFile)
                            Log.d("Camera", "Photo capture succeeded: $savedUri")

                            // Load the image bitmap from the saved Uri
                            val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(savedUri))
                            imageBitmap.value = bitmap
                            Log.d("Locationx", "onImageSaved: ${bitmap.height}, ${bitmap.width}")
                            Toast.makeText(context, "Photo capture succeeded: $savedUri", Toast.LENGTH_SHORT).show()
                        }

                        override fun onError(exception: ImageCaptureException) {
                            // Handle the error here
                            Log.e("Camera", "Photo capture failed: ${exception.message}", exception)
                        }
                    })
                    /**
                     * navController.navigate("meter/${meterId}/$photoFile")
                     */
                    navController.navigate("meter/${meterId}")
                    meterViewModel.setPhotoUrl(photoFile.toString())
                }
            )
        }
    ) {paddingValues ->
        AndroidView(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            factory = { context ->
                PreviewView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    setBackgroundColor(Color.BLUE)
                    scaleType = PreviewView.ScaleType.FILL_START
                }.also {previewView ->
                    previewView.controller = cameraCotroller

                    cameraCotroller.bindToLifecycle(lifeCycleOwner)

                }
            })
    }
}

