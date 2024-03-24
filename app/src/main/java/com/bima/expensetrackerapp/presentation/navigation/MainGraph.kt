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
import com.bima.expensetrackerapp.presentation.component.expense.ExpenseDetail
import com.bima.expensetrackerapp.presentation.component.expense.UpdateExpense
import com.bima.expensetrackerapp.presentation.component.income.AddIncome
import com.bima.expensetrackerapp.presentation.component.income.IncomeDetail
import com.bima.expensetrackerapp.presentation.component.income.UpdateIncome
import com.bima.expensetrackerapp.presentation.component.settings.category.CategorySettings

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
                onNavigateToAuth = {
                    rootNavController.navigate(route = Graph.AUTH) {
                        popUpTo(Graph.ROOT)
                    }
                })
        }
        composable(
            route = Screen.SettingScreen.route
        ) {
            SettingsScreen(navController = navController)
        }
        composable(
            route = Screen.CategorySettingScreen.route
        ) {
            CategorySettings(navController = navController)
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
            route = Screen.IncomeDetailScreen.route + "{id}"
        ) {navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString("id")
            if (id != null) {
                IncomeDetail(id = id, navController = navController)
            }
        }
        composable(
            route = Screen.UpdateIncomeScreen.route + "{id}"
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString("id")
            if (id != null) {
                UpdateIncome(id = id, navController = navController)
            }
        }
        composable(
            route = Screen.ExpenseDetailScreen.route + "{id}"
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString("id")
            if (id != null) {
                ExpenseDetail(id = id, navController = navController)
            }
        }
        composable(
            route = Screen.UpdateExpenseScreen.route + "{id}"
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString("id")
            if (id != null) {
                UpdateExpense(id = id, navController = navController)
            }
        }
    }
}