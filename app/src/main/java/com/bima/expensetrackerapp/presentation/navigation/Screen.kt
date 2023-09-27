package com.bima.expensetrackerapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route:String,val name:String ,val icon: ImageVector) {
    object Title: Screen("title_screen","Title", Icons.Filled.Home)
    object Login: Screen("login_screen","Login", Icons.Filled.Home)
    object HomeScreen: Screen("home_screen","Home", Icons.Filled.Search)
}
