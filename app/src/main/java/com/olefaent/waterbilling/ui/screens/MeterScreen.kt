package com.olefaent.waterbilling.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.olefaent.waterbilling.R
import com.olefaent.waterbilling.model.Meter
import com.olefaent.waterbilling.ui.screens.components.ErrorScreen
import com.olefaent.waterbilling.ui.screens.components.LoadingScreen
import kotlinx.coroutines.delay


@Composable
fun MeterScreen(
    meterId: Int,
    navController: NavController? = null,
) {
    val meterViewModel : MeterViewModel = viewModel(factory = MeterViewModel.Factory)
    val uiState = meterViewModel.uiState
    val photoFile = meterViewModel.photo_url.value
//    val mUri = Uri.fromFile(photoFile)
    Log.d("MeterScreen photo", "MeterScreen: $photoFile")

    val valueMeter = meterViewModel.setMeterId(meterId)
    Log.d("Meter", "MeterScreen: $valueMeter")
    val eii = meterViewModel.getMeter(meterId)

    Log.d("Meter /state1", "MeterScreen: $uiState, $eii")

    when (uiState){
        is OneMeterState.Loading -> LoadingScreen()
        is OneMeterState.Success -> OneMeterScreen(meter = uiState.meter, navController = navController, photoFile = photoFile)
        is OneMeterState.Error -> ErrorScreen(retryAction = {})
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OneMeterScreen(
    meter: Meter,
    modifier: Modifier = Modifier ,
    photoFile: String = "",
    navController: NavController? = null,
){
    Scaffold(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        topBar = {
            MTopBar(
                goBack = {
                    navController?.popBackStack()
                }
            )
        }
    ){ innerPadding ->
        Column (
            modifier = modifier.padding(innerPadding)
        )   {
            Card (
                modifier = modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .padding(12.dp)
                
            ){
                AsyncImage(
                    modifier = modifier.fillMaxSize(),
                    model = "https://c8.alamy.com/comp/CNPAG5/close-up-image-of-a-newly-installed-brass-water-meter-in-a-suburban-CNPAG5.jpg",
                    contentDescription = "Water Meter Image"
                )
            }
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(imageVector = Icons.Default.Info, contentDescription = "Build Icon")
                    Text(text = "Meter Type: "+ meter.meterType)
                }

                Row(
                    modifier = modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Build Icon")
                    Text(text = "Location: "+meter.gpsCoordinates)
                }


                Row (
                    modifier = modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Date Icon")
                    Text(text = "Installation Date "+ meter.installationDate)
                }
                Row(
                    modifier = modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Build, contentDescription = "Build Icon")
                    Text(
                        modifier = modifier.padding(12.dp),
                        text = "Meter Reading: 234"
                    )
                }


            }
            BottomButton(
                meterNumber = 10,
                suc = {
                    navController?.navigate("meters"){
                        popUpTo("meters"){
                            inclusive = true
                        }
                    }
                }
            )
        }
    }

}

//Success dialog
@Composable
fun SuccessDialog(){
    var showSuccessDialog by remember { mutableStateOf(false) }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Success") },
            text = {
                   Icon(
                       imageVector = Icons.Default.CheckCircle,
                       contentDescription = "Success",
                       modifier = Modifier.size(24.dp)
                   )
            },
            confirmButton = {
                Button(onClick = { showSuccessDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}



@Composable
fun BottomButton(meterNumber: Int, modifier: Modifier = Modifier, suc : () -> Unit = {}){
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Check the readings") },
            text = {
                Text(
                    text = "Is the reading $meterNumber"
                )
                   },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        suc()
                    }
                ) {
                    Text("Yes, Send")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("No! Try Again")
                }
            }
        )

        LaunchedEffect(key1 = showDialog) {
            delay(2 * 60 * 1000L) // delay for 2 minutes
            showDialog = false
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ){
        Button(
            onClick = { showDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(text = "Send Readings")
            Icon(
                imageVector =  Icons.Default.Send,
                contentDescription = "Send Stuff",
                modifier = modifier.padding(start = 12.dp)
            )

        }
    }
}

@Composable
fun MTopBar(
    modifier: Modifier = Modifier,
    goBack : () -> Unit = {}
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(
            onClick = {
                Log.d("Meter", "OneMeterScreen: hello world")
                goBack()
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_left),
                contentDescription = "Back",
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = "Meter Info",
            fontSize = 20.sp
        )
    }
}


//@Composable
//fun shimmerBrush(showShimmer: Boolean = true,targetValue:Float = 1000f): Brush {
//    return if (showShimmer) {
//        val shimmerColors = listOf(
//            Color.LightGray.copy(alpha = 0.6f),
//            Color.LightGray.copy(alpha = 0.2f),
//            Color.LightGray.copy(alpha = 0.6f),
//        )
//
//        val transition = rememberInfiniteTransition()
//        val translateAnimation = transition.animateFloat(
//            initialValue = 0f,
//            targetValue = targetValue,
//            animationSpec = infiniteRepeatable(
//                animation = tween(800), repeatMode = RepeatMode.Reverse
//            )
//        )
//        Brush.linearGradient(
//            colors = shimmerColors,
//            start = Offset.Zero,
//            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
//        )
//    } else {
//        Brush.linearGradient(
//            colors = listOf(Color.Transparent,Color.Transparent),
//            start = Offset.Zero,
//            end = Offset.Zero
//        )
//    }
//}


@Preview(showBackground = true)
@Composable
fun OneMeterScreenPreview() {
    val meter = Meter(
        meterNumber = "123456789",
        gpsCoordinates = "123456789",
        installationDate = "123456789",
        meterType = "123456789",
        id = 1,
        createdAt = "123456789",
        customerId = 1,
    )

    OneMeterScreen(meter = meter)

}

@Preview(showBackground = true)
@Composable
fun BottomButtonPreview(){
    SuccessDialog()
}