package com.olefaent.waterbilling.ui.screens

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.olefaent.waterbilling.model.Customer
import com.olefaent.waterbilling.model.Meter
import com.olefaent.waterbilling.ui.screens.components.ErrorScreen
import com.olefaent.waterbilling.ui.screens.components.LoadingScreen

@Composable
fun MetersScreen(
    meterState: MeterState,
    navController: NavController,
    modifier: Modifier = Modifier,
){
    when (meterState){
        is MeterState.Loading -> LoadingScreen()
        is MeterState.Success -> MetersList(navController = navController, meters = meterState.meters)
        is MeterState.Error -> ErrorScreen(retryAction = {})
        else -> {}
    }

}

@Composable
fun MetersList(
    meters: List<Meter>,
    navController: NavController,
    modifier: Modifier = Modifier,
){
    LazyColumn(modifier = modifier){
        items(meters.size){ index ->
            MetersItem(navController=navController, meter = meters[index])
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetersItem(
    meter: Meter,
    navController: NavController,
    modifier: Modifier = Modifier,
){
    ListItem(
        headlineText = { Text(text = meter.meterNumber) },
        leadingContent = {
            Icon(
                Icons.Filled.Settings,
                contentDescription = "Localized description",
            )
        },
        supportingText = { Text(text = meter.meterType) },
        tonalElevation = 10.dp,
        trailingContent = {
            Button(onClick = {
                navController.navigate("camera/${meter.id}")

                Log.d("btnCheck", "MetersItem: ${meter.meterNumber}")
            }) {
                Text(text = "Capture Meter Reading")
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun MetersScreenPreview(){
    val meter = Meter(
        id = 1,
        meterNumber = "NWC1",
        meterType = "domestic",
        customerId = 1,
        installationDate = "2021-01-01",
        gpsCoordinates = "123.123, 123.123",
        createdAt = "2021-01-01",
    )
//    MetersItem(meter = meter)
}