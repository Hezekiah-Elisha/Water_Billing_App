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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.olefaent.waterbilling.R
import com.olefaent.waterbilling.model.Meter
import com.olefaent.waterbilling.ui.theme.poppins

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

        Row(
            modifier = modifier.padding(15.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ){
            CircularImage()

            Column {
                Text(
                    text = "Hello ${user?.username}!",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(5.dp),
                    fontFamily = poppins
                )
                Text(
                    text = "Welcome back!",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(2.dp),
                    fontFamily = poppins
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

                    context.finish()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = "Exit Icon",
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Logout",
                    modifier = Modifier.padding(5.dp),
                    fontFamily = poppins
                )
            }
        }

        SearchBar()
        Text(
            text = "Available Meters",
            modifier = Modifier.padding(15.dp),
            style = MaterialTheme.typography.headlineMedium,
            fontFamily = poppins
        )
        val meters = listOf(
            Meter(
                id = 1,
                meterNumber = "NWC1",
                meterType = "domestic",
                customerId = 1,
                installationDate = "2021-01-01",
                gpsCoordinates = "123.123, 123.123",
                createdAt = "2021-01-01"
            ),
            Meter(
                id = 2,
                meterNumber = "NWC2",
                meterType = "domestic",
                customerId = 1,
                installationDate = "2021-01-01",
                gpsCoordinates = "123.123, 123.123",
                createdAt = "2021-01-01"
            ),
            Meter(
                id = 3,
                meterNumber = "NWC3",
                meterType = "domestic",
                customerId = 1,
                installationDate = "2021-01-01",
                gpsCoordinates = "123.123, 123.123",
                createdAt = "2021-01-01"
            ),
            Meter(
                id = 4,
                meterNumber = "NWC4",
                meterType = "domestic",
                customerId = 1,
                installationDate = "2021-01-01",
                gpsCoordinates = "123.123, 123.123",
                createdAt = "2021-01-01"
            ),
            Meter(
                id = 5,
                meterNumber = "NWC5",
                meterType = "domestic",
                customerId = 1,
                installationDate = "2021-01-01",
                gpsCoordinates = "123.123, 123.123",
                createdAt = "2021-01-01"
            ),
            Meter(
                id = 6,
                meterNumber = "NWC6",
                meterType = "domestic",
                customerId = 1,
                installationDate = "2021-01-01",
                gpsCoordinates = "123.123, 123.123",
                createdAt = "2021-01-01"
            ),
            Meter(
                id = 7,
                meterNumber = "NWC7",
                meterType = "domestic",
                customerId = 1,
                installationDate = "2021-01-01",
                gpsCoordinates = "123.123, 123.123",
                createdAt = "2021-01-01"
            ),
            Meter(
                id = 8,
                meterNumber = "NWC8",
                meterType = "domestic",
                customerId = 1,
                installationDate = "2021-01-01",
                gpsCoordinates = "123.123, 123.123",
                createdAt = "2021-01-01"
            ),
            Meter(
                id = 9,
                meterNumber = "NWC9",
                meterType = "domestic",
                customerId = 1,
                installationDate = "2021-01-01",
                gpsCoordinates = "123.123, 123.123",
                createdAt = "2021-02-01"
            ),
            Meter(
                id = 10,
                meterNumber = "NWC10",
                meterType = "domestic",
                customerId = 1,
                installationDate = "2021-02-01",
                gpsCoordinates = "123.123, 123.123",
                createdAt = "2021-02-01"
            ),
            Meter(
                id = 11,
                meterNumber = "NWC11",
                meterType = "domestic",
                customerId = 1,
                installationDate = "2021-02-01",
                gpsCoordinates = "123.123, 123.123",
                createdAt = "2021-02-01"
            ),
        )
        MetersList(meters = meters)


//        IntroPart(name = user?.username ?: "None")
//        Row{
////            CircularImage()
//            Text(
//                text = "Hello ${user?.username}!",
//                textAlign = TextAlign.Center,
//                style = MaterialTheme.typography.headlineMedium,
//                modifier = Modifier.padding(15.dp)
//            )
//            Text(
//                text = "Welcome back!",
//            )
//        }
//        Card {
//            Text(
//                text = "This is a card",
//                modifier = Modifier.padding(15.dp)
//            )
//        }
//        Button(
//            onClick = {
//                userViewModel.logout()
//                navController.navigate("login"){
//                    popUpTo("splash"){
//                        inclusive = true
//                    }
//                }
//            },
//            modifier = modifier.padding(20.dp)){
//            Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = "Logout Icon")
//            Text(text = "Logout")
//        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier){
    var meterNumber by rememberSaveable { mutableStateOf("") }
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp),
        value = meterNumber,
        onValueChange = { meterNumber = it },
        label = { Text(text = "Meter Number", fontFamily= poppins) },
        supportingText = { Text("Enter your meter number") },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Person, contentDescription = "Email")
        },
        trailingIcon = {
            Icon(imageVector = Icons.Filled.Search, contentDescription = "Email")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
        shape = RoundedCornerShape(20.dp),
    )
}

@Composable
fun MetersList(meters: List<Meter>, modifier: Modifier = Modifier){
    LazyColumn(modifier = modifier){
        items(meters.size){ index ->
//            MetersItem(navController=navController, meter = meters[index])
            MeterRow(meter = meters[index])
        }
    }
}

@Composable
fun MeterRow(meter: Meter, modifier: Modifier = Modifier){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = meter.meterNumber,
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.headlineSmall,
            fontFamily = poppins,
            textAlign = TextAlign.Center
        )
        Text(
            text = meter.meterType,
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.bodySmall,
            fontFamily = poppins,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CircularImage(modifier: Modifier = Modifier){
    val image = painterResource(id = R.drawable.pp )
    Image(
        painter = image,
        contentDescription = "Profile Picture",
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center,
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
//    MetersList()
    val meters = listOf(
        Meter(
            id = 1,
            meterNumber = "NWC1",
            meterType = "domestic",
            customerId = 1,
            installationDate = "2021-01-01",
            gpsCoordinates = "123.123, 123.123",
            createdAt = "2021-01-01"
        ),
        Meter(
            id = 2,
            meterNumber = "NWC2",
            meterType = "domestic",
            customerId = 1,
            installationDate = "2021-01-01",
            gpsCoordinates = "123.123, 123.123",
            createdAt = "2021-01-01"
        ),
        Meter(
            id = 3,
            meterNumber = "NWC3",
            meterType = "domestic",
            customerId = 1,
            installationDate = "2021-01-01",
            gpsCoordinates = "123.123, 123.123",
            createdAt = "2021-01-01"
        ),
        Meter(
            id = 4,
            meterNumber = "NWC4",
            meterType = "domestic",
            customerId = 1,
            installationDate = "2021-01-01",
            gpsCoordinates = "123.123, 123.123",
            createdAt = "2021-01-01"
        ),
        Meter(
            id = 5,
            meterNumber = "NWC5",
            meterType = "domestic",
            customerId = 1,
            installationDate = "2021-01-01",
            gpsCoordinates = "123.123, 123.123",
            createdAt = "2021-01-01"
        ),
        Meter(
            id = 6,
            meterNumber = "NWC6",
            meterType = "domestic",
            customerId = 1,
            installationDate = "2021-01-01",
            gpsCoordinates = "123.123, 123.123",
            createdAt = "2021-01-01"
        ),
        Meter(
            id = 7,
            meterNumber = "NWC7",
            meterType = "domestic",
            customerId = 1,
            installationDate = "2021-01-01",
            gpsCoordinates = "123.123, 123.123",
            createdAt = "2021-01-01"
        ),
        Meter(
            id = 8,
            meterNumber = "NWC8",
            meterType = "domestic",
            customerId = 1,
            installationDate = "2021-01-01",
            gpsCoordinates = "123.123, 123.123",
            createdAt = "2021-01-01"
        ),
        Meter(
            id = 9,
            meterNumber = "NWC9",
            meterType = "domestic",
            customerId = 1,
            installationDate = "2021-01-01",
            gpsCoordinates = "123.123, 123.123",
            createdAt = "2021-02-01"
        ),
        Meter(
            id = 10,
            meterNumber = "NWC10",
            meterType = "domestic",
            customerId = 1,
            installationDate = "2021-02-01",
            gpsCoordinates = "123.123, 123.123",
            createdAt = "2021-02-01"
        ),
        Meter(
            id = 11,
            meterNumber = "NWC11",
            meterType = "domestic",
            customerId = 1,
            installationDate = "2021-02-01",
            gpsCoordinates = "123.123, 123.123",
            createdAt = "2021-02-01"
        ),
    )
    MetersList(meters)
}