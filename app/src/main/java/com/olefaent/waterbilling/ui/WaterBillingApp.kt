package com.olefaent.waterbilling.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.olefaent.waterbilling.ui.screens.BillingViewModel
import com.olefaent.waterbilling.ui.screens.CameraScreen
import com.olefaent.waterbilling.ui.screens.CustomScaffold
import com.olefaent.waterbilling.ui.screens.CustomersScreen
import com.olefaent.waterbilling.ui.screens.HomeScreen
import com.olefaent.waterbilling.ui.screens.LoginScreen
import com.olefaent.waterbilling.ui.screens.MeterScreen
import com.olefaent.waterbilling.ui.screens.MeterViewModel
import com.olefaent.waterbilling.ui.screens.MetersScreen
import com.olefaent.waterbilling.ui.screens.SplashScreen
import com.olefaent.waterbilling.ui.utils.BottomNav
import com.olefaent.waterbilling.ui.utils.NAV_ITEMS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaterBillingApp(){
    val navController = rememberNavController()
    val billingViewModel: BillingViewModel = viewModel(factory = BillingViewModel.Factory)
    val meterViewModel : MeterViewModel = viewModel(factory = MeterViewModel.Factory)


    NavigationGraph(navController = navController, billingViewModel = billingViewModel, meterViewModel = meterViewModel)

}

/**
 * This is the navigation graph
 *
 * @param navController
 * @param billingViewModel
 * @param modifier
 *
 * @author Hezekiah Elisha
 *
 * @since 1.0.0
 */

@Composable
fun NavigationGraph(
    root : String = "splash",
    navController: NavHostController,
    billingViewModel: BillingViewModel,
    meterViewModel: MeterViewModel,
){

    NavHost(navController = navController, startDestination = root ){
        composable(BottomNav.Home.route){
            /**
             * This is the home screen
             * /TODO/
             * 1. Add a welcome message
             * 2. Add a logout button
             * 3. Add a button to navigate to the customers screen
             * 4. Add a button to navigate to the meters screen
             * 5. Add a button to navigate to the settings screen
             * 6. Add navcontroller as a parameter to the home screen
             * - navController = [navController]
             *
             */
            HomeScreen(navController = navController, viewModel = billingViewModel)
        }
        composable(BottomNav.Customers.route){
            CustomersScreen(navController =navController , uiState = billingViewModel.uiState)
        }
        composable(BottomNav.Meters.route){
            MetersScreen(navController = navController, meterState = billingViewModel.meterState, meterViewModel = meterViewModel)
        }
        composable(
            "meter/{meterId}",
            arguments = listOf(
                navArgument("meterId"){
                    type = NavType.IntType
                }
            )
            ){
            MeterScreen(
                meterId = it.arguments?.getInt("meterId") ?: 0,
                navController = navController,
                meterViewModel = meterViewModel
            )
        }
        composable("camera/{meterId}",
            arguments = listOf(
                navArgument("meterId"){
                    type = NavType.IntType
                })
        ){
            CameraScreen(
                meterId = it.arguments?.getInt("meterId") ?: 0,
                navController = navController,
                meterViewModel = meterViewModel
            )
        }
        composable("splash"){
            SplashScreen(navController = navController)
        }
        composable("login"){
            LoginScreen(navController = navController)
        }
        composable("custom"){
            CustomScaffold()
        }
    }

}

@Composable
fun BottomNavigation(navController: NavController){
    val items = NAV_ITEMS
    var selectedItemIndex by rememberSaveable {
        mutableStateOf("home")
    }

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEachIndexed { _, navItems ->
            NavigationBarItem(
                selected = currentRoute == navItems.route,
                onClick = {
                    selectedItemIndex = navItems.route
                    navController.navigate(navItems.route){
                        navController.graph.startDestinationRoute?.let {route->
                            popUpTo(route){
                                saveState = true
                            }
                            selectedItemIndex = navItems.route
                        }
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                alwaysShowLabel = true,
                icon = {
                    (if (selectedItemIndex == navItems.route) navItems.selectedIcon else navItems.unselectedIcon)?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = navItems.title
                        )
                    }
                },
                label = {
                    Text(text = navItems.title)
                }
            )
        }
    }


}