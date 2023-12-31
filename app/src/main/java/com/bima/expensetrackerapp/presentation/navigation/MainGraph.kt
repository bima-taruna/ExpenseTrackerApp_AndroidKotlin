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
import com.bima.expensetrackerapp.presentation.component.income.AddIncome
import com.bima.expensetrackerapp.presentation.component.income.IncomeForm
import com.bima.expensetrackerapp.presentation.component.transaction.TransactionDetail

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainGraph(
    navController: NavHostController,
    rootNavController: NavHostController,
) {
    NavHost(
        navController = navController,
        route = Graph.MAIN,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(
            route = Screen.HomeScreen.route
        ) {
            HomeScreen(
                onNavigateToAuth = {
                    rootNavController.navigate(route = Graph.AUTH) {
                        popUpTo(Graph.ROOT)
                    }
                },
                navController = navController
                )
        }
        composable(
            route = Screen.StatScreen.route
        ) {
            StatisticScreen()
        }
        composable(
            route = Screen.ProfileScreen.route
        ) {
            ProfileScreen(
                navController = navController,
                onNavigateToAuth = {
                    rootNavController.navigate(route = Graph.AUTH) {
                        popUpTo(Graph.ROOT)
                    }
                })
        }
        composable(
            route = Screen.SettingScreen.route
        ) {
            SettingsScreen()
        }
        composable(
            route = Screen.ExpenseFormScreen.route
        ) {
            AddExpense(navController = navController)
        }
        composable(
            route = Screen.IncomeFormScreen.route
        ) {
            AddIncome(navController = navController)
        }
        composable(
            route = Screen.TransactionDetailScreen.route + "{id}"
        ) {navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString("id")
            id?.let {
                TransactionDetail(id = it)
            }
        }
    }
}