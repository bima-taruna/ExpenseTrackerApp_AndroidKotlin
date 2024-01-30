package com.bima.expensetrackerapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route:String,val name:String ,val icon: ImageVector?) {
    data object HomeScreen: Screen("home_screen","Home", Icons.Filled.Home)
    data object StatScreen: Screen("stat_screen","Statistic", Icons.Default.AccountCircle)
    data object ProfileScreen: Screen("profile_screen","Profile", Icons.Filled.AccountCircle)
    data object SettingScreen: Screen("setting_screen","Setting", Icons.Filled.Settings)
    data object ExpenseFormScreen: Screen("expense_form","Expense", null)
    data object IncomeFormScreen: Screen("income_form","Income", null)
    data object IncomeDetailScreen:Screen("income_detail_screen/", "income_detail", null)
    data object ExpenseDetailScreen:Screen("expense_detail_screen/", "expense_detail", null)
//    data object TransactionDetailScreen: Screen("detail_screen/", "detail", icon = null)
}
