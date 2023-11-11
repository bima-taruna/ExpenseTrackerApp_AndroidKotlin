package com.bima.expensetrackerapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route:String,val name:String ,val icon: ImageVector?) {
    object Title: Screen("title_screen","Title", Icons.Filled.Home)
    object Login: Screen("login_screen","Login", Icons.Filled.Home)
    object HomeScreen: Screen("home_screen","Home", Icons.Filled.Home)
    object StatScreen: Screen("stat_screen","Statistic", Icons.Default.AccountCircle)
    object ProfileScreen: Screen("profile_screen","Profile", Icons.Filled.AccountCircle)
    object SettingScreen: Screen("setting_screen","Setting", Icons.Filled.Settings)
    object ExpenseFormScreen: Screen("expense_form","Expense", null)
    object IncomeFormScreen: Screen("income_form","Income", null)
}
