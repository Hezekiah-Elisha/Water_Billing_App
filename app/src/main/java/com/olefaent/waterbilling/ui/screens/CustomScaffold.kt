package com.olefaent.waterbilling.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.olefaent.waterbilling.ui.BottomNavigation
import com.olefaent.waterbilling.ui.NavigationGraph
import com.olefaent.waterbilling.ui.utils.BottomNav

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomScaffold(){
    val navController = rememberNavController()
    val billingViewModel: BillingViewModel = viewModel(factory = BillingViewModel.Factory)
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigation(navController)
        }
    ){ innerpadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding)
        ) {
            NavigationGraph(root = BottomNav.Home.route,navController = navController, billingViewModel = billingViewModel)
        }
    }
}

/**
 * Preview
 *
 * @author: Hezekiah Elisha
 */
@Preview(showBackground = true)
@Composable
fun CustomScaffoldPreview(){
    CustomScaffold()
}