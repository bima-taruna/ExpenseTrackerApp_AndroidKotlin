package com.bima.expensetrackerapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bima.expensetrackerapp.presentation.TitleScreenRoot
import com.bima.expensetrackerapp.presentation.login.LoginScreenRoot

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTH,
        startDestination = AuthScreen.Title.route
    ) {
        composable(route = AuthScreen.Title.route) {
            TitleScreenRoot(navController = navController)
        }
        composable(route = AuthScreen.Login.route) {
           LoginScreenRoot(navController = navController)
        }
    }

}