package com.olefaent.waterbilling.ui.screens

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    meterId: Int,
    navController: NavController,
    meterViewModel: MeterViewModel
){
    val applicationContext = LocalContext.current.applicationContext

    val controller = remember {
        LifecycleCameraController(applicationContext).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE or
                        CameraController.VIDEO_CAPTURE
            )
        }
    }

    val cameraPermissionState: PermissionState = rememberPermissionState(
        Manifest.permission.CAMERA
    )
    val readExternalStoragePermissionState: PermissionState = rememberPermissionState(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    val writeExternalStoragePermissionState: PermissionState = rememberPermissionState(
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
//    val cameraPermissionState = rememberPermissionState(
////        permissions = android.Manifest.permission.CAMERA
//        listOf(android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//    )
    var hasPermission = false
    if (cameraPermissionState.status.isGranted && readExternalStoragePermissionState.status.isGranted && writeExternalStoragePermissionState.status.isGranted){
        hasPermission = true
    }

    MainContent(
        navController = navController,
        meterId = meterId,
        context = applicationContext,
        hasPermission = hasPermission,
        meterViewModel = meterViewModel,
        controller = controller,
        onPermissionRequest = {
            cameraPermissionState.launchPermissionRequest()
            readExternalStoragePermissionState.launchPermissionRequest()
            writeExternalStoragePermissionState.launchPermissionRequest()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    navController: NavController,
    meterId: Int,
    context: Context,
    hasPermission: Boolean,
    meterViewModel: MeterViewModel,
    controller: LifecycleCameraController,
    modifier: Modifier = Modifier,
    onPermissionRequest: () -> Unit
){
    if (hasPermission){
//        CameraContent(
//            meterId = meterId,
//            navController = navController,
//            meterViewModel = meterViewModel
//        )
        Scaffold { padding->
            Box(
                modifier = modifier
                    .padding(padding)
                    .fillMaxSize()
            ){
                CameraPreview(
                    controller = controller,
                    modifier = modifier
                        .padding(padding)
                        .fillMaxSize()
                )
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    IconButton(
                        modifier = modifier
                            .background(
                                color = MaterialTheme.colorScheme.background,
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(16.dp),
                        onClick = {
                            takePhoto(
                                controller = controller,
                                applicationContext = context
                            ) { bitmap ->
                                meterViewModel.bitmap = bitmap.asImageBitmap()
                                navController.navigate("meter/${meterId}")
                            }
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Take Photo")
                    }
                }

            }
        }

    } else {
        PermissionRequest(onPermissionRequest)
    }

}

@Composable
fun PermissionRequest(
    onPermissionRequest: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Button(
            onClick = onPermissionRequest
        ){
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Request permission"
            )
            Text("Request permission")
        }
    }
}

private fun takePhoto(
    controller: LifecycleCameraController,
    applicationContext: Context,
    onPhotoTaken: (Bitmap) -> Unit
){


    controller.takePicture(
        ContextCompat.getMainExecutor(applicationContext),
        object: ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)

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

                onPhotoTaken(rotatedBitmap)
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)

                Log.e("Camera", "onError: Couldn't take photo ", exception )
            }
        }
    )
}