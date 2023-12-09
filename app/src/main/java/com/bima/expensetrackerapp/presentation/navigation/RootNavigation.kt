package com.bima.expensetrackerapp.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bima.expensetrackerapp.presentation.ExpenseTrackerApp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavigation(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        route = Graph.ROOT,
        startDestination = Graph.AUTH
    ) {
        authNavGraph(navHostController)
        composable(route = Graph.MAIN) {
            ExpenseTrackerApp(rootNavController = navHostController)
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTH = "auth_graph"
    const val MAIN = "main_graph"
}