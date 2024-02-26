package com.bima.expensetrackerapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bima.expensetrackerapp.presentation.login.LoginScreen
import com.bima.expensetrackerapp.presentation.TitleScreen
import com.bima.expensetrackerapp.presentation.login.LoginScreenRoot

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTH,
        startDestination = AuthScreen.Title.route
    ) {
        composable(route = AuthScreen.Title.route) {
            TitleScreen(navController = navController)
        }
        composable(route = AuthScreen.Login.route) {
           LoginScreenRoot(navController = navController)
        }
    }

}