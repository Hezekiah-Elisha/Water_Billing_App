package com.olefaent.waterbilling.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import com.olefaent.waterbilling.ui.NavItems

sealed class BottomNav(val route: String, val title: String) {
    object Home : BottomNav("home", "Home")
    object Customers : BottomNav("customers", "Customers")
    object Meters : BottomNav("meters", "Meters")
    object Meter : BottomNav("meter", "meter")
}

val NAV_ITEMS = listOf(
    NavItems(route = BottomNav.Home.route, title = BottomNav.Home.title, selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home),
    NavItems(route = BottomNav.Customers.route, title = BottomNav.Customers.title, selectedIcon = Icons.Filled.Face, unselectedIcon = Icons.Outlined.Face),
    NavItems(route = BottomNav.Meters.route, title = BottomNav.Meters.title, selectedIcon = Icons.Filled.Build, unselectedIcon = Icons.Outlined.Build),
)
