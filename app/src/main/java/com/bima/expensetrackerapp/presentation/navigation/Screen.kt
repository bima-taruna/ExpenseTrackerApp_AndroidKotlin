package com.bima.expensetrackerapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route:String,val name:String ,val icon: ImageVector? = null) {
    data object HomeScreen: Screen("home_screen","Home", Icons.Filled.Home)
    data object StatScreen: Screen("stat_screen","Statistic", Icons.Default.AccountCircle)
    data object ProfileScreen: Screen("profile_screen","Profile", Icons.Filled.AccountCircle)
    data object SettingScreen: Screen("setting_screen","Setting", Icons.Filled.Settings)
    data object CategorySettingScreen: Screen("category_setting_screen","Category Setting")
    data object ExpenseFormScreen: Screen("expense_form","Expense")
    data object IncomeFormScreen: Screen("income_form","Income")
    data object IncomeDetailScreen:Screen("income_detail_screen/", "income_detail")
    data object UpdateIncomeScreen:Screen("update_income_screen/", "update_income")
    data object ExpenseDetailScreen:Screen("expense_detail_screen/", "expense_detail")
    data object UpdateExpenseScreen:Screen("update_expense_screen/", "update_expense")

}
