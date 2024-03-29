package com.bima.expensetrackerapp.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bima.expensetrackerapp.presentation.HomeScreen
import com.bima.expensetrackerapp.presentation.ProfileScreen
import com.bima.expensetrackerapp.presentation.SettingsScreen
import com.bima.expensetrackerapp.presentation.StatisticScreen
import com.bima.expensetrackerapp.presentation.component.expense.AddExpense

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationTest(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(
            route = Screen.HomeScreen.route
        ) {
            HomeScreen( navController = navController)
        }
        composable(
            route = Screen.StatScreen.route
        ) {
            StatisticScreen()
        }
        composable(
            route = Screen.ProfileScreen.route
        ) {
            ProfileScreen(onNavigateToAuth = {})
        }
        composable(
            route = Screen.SettingScreen.route
        ) {
            SettingsScreen(navController = navController)
        }
        composable(
            route = Screen.ExpenseFormScreen.route
        ) {
            AddExpense(navController = navController)
        }
        composable(
            route = Screen.IncomeFormScreen.route
        ) {
        }
    }
}