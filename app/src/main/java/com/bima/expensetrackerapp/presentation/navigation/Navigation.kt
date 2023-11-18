package com.bima.expensetrackerapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bima.expensetrackerapp.presentation.HomeScreen
import com.bima.expensetrackerapp.presentation.ProfileScreen
import com.bima.expensetrackerapp.presentation.SettingsScreen
import com.bima.expensetrackerapp.presentation.SignInScreen
import com.bima.expensetrackerapp.presentation.StatisticScreen
import com.bima.expensetrackerapp.presentation.TitleScreen
import com.bima.expensetrackerapp.presentation.component.expense.ExpenseForm
import com.bima.expensetrackerapp.presentation.component.income.IncomeForm

@Composable
fun Navigation(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Title.route
    ) {
        composable(
            route = Screen.Title.route
        ) {
            TitleScreen(navController = navController)
        }
        composable(
            route = Screen.Login.route
        ) {
            SignInScreen(navController = navController)
        }
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