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
    navController: NavController
){
    val cameraPermissionState: PermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    MainContent(
        navController = navController,
        meterId = meterId,
        hasPermission = cameraPermissionState.status.isGranted ,
        onPermissionRequest = { cameraPermissionState.launchPermissionRequest() }
    )
}

@Composable
fun MainContent(
    navController: NavController,
    meterId: Int,
    hasPermission: Boolean,
    onPermissionRequest: () -> Unit
){
    if (hasPermission){
        CameraContent(
            meterId = meterId,
            navController = navController
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