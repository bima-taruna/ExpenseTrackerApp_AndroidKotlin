package com.bima.expensetrackerapp.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bima.expensetrackerapp.presentation.navigation.BottomNavigation
import com.bima.expensetrackerapp.presentation.navigation.Navigation
import com.bima.expensetrackerapp.presentation.navigation.Screen

@Composable
fun ExpenseTrackerApp(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val bottomNavigationItems = listOf(
        Screen.HomeScreen,
        Screen.StatScreen,
        Screen.ProfileScreen,
        Screen.SettingScreen
    )
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar = {
            BottomNavigation(navController = navController, screens = bottomNavigationItems)
        },
        content = {innerPadding ->
            Box(modifier = modifier.padding(innerPadding)){
                Navigation(navController = navController)
            }
        }
    )

}