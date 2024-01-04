package com.olefaent.waterbilling.ui.screens

import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.olefaent.waterbilling.R
import com.olefaent.waterbilling.ui.theme.poppins
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun SplashScreen(
    navController: NavController,
    modifier: Modifier = Modifier
){
    val userViewModel: UserViewModel = viewModel(
        factory = UserViewModel.Factory
    )
    val loggedIn = userViewModel.isLoggedIn()

    Column (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.water),
            contentDescription = "Water Billing App"
        )
        Text(
            fontSize = 30.sp,
            text = "Water Billing App",
            fontFamily = poppins,
            textAlign = TextAlign.Center
        )
        LaunchedEffect(key1 = true){
            withContext(Dispatchers.Main){
                scaleIn(
                    animationSpec = tween(
                        durationMillis = 500
                    )
                )
            }

            delay(2000)

            if (loggedIn){
                navController.navigate("custom"){
                    popUpTo("splash"){
                        inclusive = true
                    }
                }
            } else {
                navController.navigate("login"){
                    popUpTo("splash"){
                        inclusive = true
                    }
                }
            }
        }
    }
}

@Composable
fun TheSplashScreen(
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.water),
            contentDescription = "Water Billing App"
        )
        Text(
            text = "Water Billing App",
            fontFamily = poppins,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun SplashScreenPreview(){
    TheSplashScreen()
}