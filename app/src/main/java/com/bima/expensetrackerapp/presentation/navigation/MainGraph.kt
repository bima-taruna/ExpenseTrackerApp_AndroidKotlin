package com.bima.expensetrackerapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bima.expensetrackerapp.presentation.HomeScreen
import com.bima.expensetrackerapp.presentation.ProfileScreen
import com.bima.expensetrackerapp.presentation.SettingsScreen
import com.bima.expensetrackerapp.presentation.StatisticScreen
import com.bima.expensetrackerapp.presentation.component.expense.ExpenseForm
import com.bima.expensetrackerapp.presentation.component.income.IncomeForm

@Composable
fun MainGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.MAIN,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(
            route = Screen.HomeScreen.route
        ) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.StatScreen.route
        ) {
            StatisticScreen()
        }
        composable(
            route = Screen.ProfileScreen.route
        ) {
            ProfileScreen(navController = navController)
        }
        composable(
            route = Screen.SettingScreen.route
        ) {
            SettingsScreen()
        }
        composable(
            route = Screen.ExpenseFormScreen.route
        ) {
            ExpenseForm(navController = navController)
        }
        composable(
            route = Screen.IncomeFormScreen.route
        ) {
            IncomeForm()
        }
    }
}