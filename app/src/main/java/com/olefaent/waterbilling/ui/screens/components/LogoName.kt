package com.olefaent.waterbilling.ui.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.olefaent.waterbilling.R
import com.olefaent.waterbilling.ui.theme.poppins

@Composable
fun LogoName(modifier : Modifier = Modifier ){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(30.dp),
        verticalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(R.drawable.water),
            contentDescription = "Water Billing App",
            modifier = modifier.size(80.dp)
        )
        Text(
            fontSize = 30.sp,
            text = "Water Billing App",
            fontFamily = poppins,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LogoNamePreview(){
    LogoName()
}