package com.olefaent.waterbilling.ui.screens

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.olefaent.waterbilling.R

@Composable
fun HomeScreen(navController: NavController , modifier: Modifier = Modifier){
    val context = LocalContext.current as Activity
    val userViewModel: UserViewModel = viewModel(
        factory = UserViewModel.Factory
    )
    val user = userViewModel.getUserLoggedIn()
    Column(
        modifier = modifier
    ) {
        IntroPart(name = user?.username ?: "None")
        Row{
//            CircularImage()
            Text(
                text = "Hello ${user?.username}!",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(15.dp)
            )
            Text(
                text = "Welcome back!",
            )
        }
        Card {
            Text(
                text = "This is a card",
                modifier = Modifier.padding(15.dp)
            )
        }
        Button(
            onClick = {
                userViewModel.logout()
                navController.navigate("login"){
                    popUpTo("splash"){
                        inclusive = true
                    }
                }
            },
            modifier = modifier.padding(20.dp)){
            Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = "Logout Icon")
            Text(text = "Logout")
        }
    }
}

@Composable
fun CircularImage(modifier: Modifier = Modifier){
    val image = painterResource(id = R.drawable.pp )
    Image(
        painter = image,
        contentDescription = "Profile Picture",
        modifier = modifier
            .size(80.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun IntroPart( name : String, modifier: Modifier = Modifier){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ){
        Box(
            modifier = Modifier.width(300.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            Card(
                shape = CircleShape,
                modifier = modifier
                    .wrapContentWidth()
                    .padding(16.dp),
                border = BorderStroke(0.6.dp, Color.Black),
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(text = name, modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                    CircularImage()
                }
            }
        }

    }
}



@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    IntroPart("Hezekiah")
}