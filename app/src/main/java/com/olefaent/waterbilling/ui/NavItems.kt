package com.olefaent.waterbilling.ui

import androidx.compose.ui.graphics.vector.ImageVector

data class NavItems (
    val route: String,
    val title: String,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null
)