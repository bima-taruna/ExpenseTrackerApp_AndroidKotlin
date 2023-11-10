package com.bima.expensetrackerapp.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    when (navBackStackEntry?.destination?.route) {
        Screen.Title.route -> {
            bottomBarState.value = false
        }
        Screen.Login.route -> {
            bottomBarState.value = false
        }
        else -> bottomBarState.value = true
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = bottomBarState.value,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                    BottomNavigation(
                        navController = navController,
                        screens = bottomNavigationItems
                    )
            }
        },
        content = {innerPadding ->
            Box(modifier = modifier.padding(innerPadding)){
                Navigation(navController = navController)
            }
        }
    )

}