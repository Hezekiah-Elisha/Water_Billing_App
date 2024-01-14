package com.olefaent.waterbilling.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    val cameraPermissionState: PermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )
    val readExternalStoragePermissionState: PermissionState = rememberPermissionState(
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    val writeExternalStoragePermissionState: PermissionState = rememberPermissionState(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
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
        hasPermission = hasPermission,
        meterViewModel = meterViewModel,
        onPermissionRequest = {
            cameraPermissionState.launchPermissionRequest()
            readExternalStoragePermissionState.launchPermissionRequest()
            writeExternalStoragePermissionState.launchPermissionRequest()
        }
    )
}

@Composable
fun MainContent(
    navController: NavController,
    meterId: Int,
    hasPermission: Boolean,
    meterViewModel: MeterViewModel,
    onPermissionRequest: () -> Unit
){
    if (hasPermission){
        CameraContent(
            meterId = meterId,
            navController = navController,
            meterViewModel = meterViewModel
        )
    } else {
        PermissionRequest(onPermissionRequest)
    }

}

@Composable
fun PermissionRequest(
    onPermissionRequest: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize()
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