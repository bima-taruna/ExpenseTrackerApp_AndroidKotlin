package com.bima.expensetrackerapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bima.expensetrackerapp.presentation.HomeScreen
import com.bima.expensetrackerapp.presentation.SignInScreen
import com.bima.expensetrackerapp.viewmodel.AuthViewModel

@Composable
fun Navigation(
    navController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel(),
) {
//    val session by remember {
//        viewModel.session
//    }.collectAsState()
//    var startDestination by remember { mutableStateOf(Screen.Login.route) }
//    LaunchedEffect(key1 = session) {
//        if (session?.user != null) {
//            startDestination = Screen.HomeScreen.route
//        }
//    }

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
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
    }
}