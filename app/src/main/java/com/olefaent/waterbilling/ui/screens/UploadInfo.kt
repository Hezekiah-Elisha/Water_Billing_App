package com.olefaent.waterbilling.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun UploadInfo(modifier: Modifier = Modifier) {
    Column {
        var meterStatus by rememberSaveable { mutableStateOf("") }
        TextField(
            value = meterStatus,
            onValueChange = { meterStatus = it },
            label = { Text("Meter Status") }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun UploadInfoPreview() {
    UploadInfo()
}