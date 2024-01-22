package com.bima.expensetrackerapp.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bima.expensetrackerapp.presentation.navigation.BottomNavigation
import com.bima.expensetrackerapp.presentation.navigation.MainGraph
import com.bima.expensetrackerapp.presentation.navigation.Screen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpenseTrackerApp(
    modifier: Modifier = Modifier,
    navController:NavHostController = rememberNavController(),
    rootNavController: NavHostController
) {
    val bottomNavigationItems = listOf(
        Screen.HomeScreen,
        Screen.StatScreen,
        Screen.ProfileScreen,
        Screen.SettingScreen
    )
    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    when (navBackStackEntry?.destination?.route) {
        Screen.ProfileScreen.route -> {
            bottomBarState.value = true
        }
        Screen.SettingScreen.route -> {
            bottomBarState.value = true
        }
        Screen.HomeScreen.route -> {
            bottomBarState.value = true
        }
        Screen.StatScreen.route -> {
            bottomBarState.value = true
        }
        else -> bottomBarState.value = false
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
                MainGraph(navController = navController, rootNavController = rootNavController)
            }
        }
    )

}