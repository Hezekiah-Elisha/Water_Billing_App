package com.olefaent.waterbilling.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.olefaent.waterbilling.R

@Composable
fun HomeScreen(modifier: Modifier = Modifier){
    val userViewModel: UserViewModel = viewModel(
        factory = UserViewModel.Factory
    )
    val user = userViewModel.getUserLoggedIn()
    Column(
        modifier = modifier
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
        ){
            Row{
                CircularImage()
                Text(
                    text = "Welcome ${user?.username}!",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(15.dp)
                )
            }
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
            .padding(15.dp)
            .size(100.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}



@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}