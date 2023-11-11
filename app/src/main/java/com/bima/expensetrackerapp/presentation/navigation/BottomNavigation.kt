package com.bima.expensetrackerapp.presentation.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bima.expensetrackerapp.R
import com.bima.expensetrackerapp.viewmodel.TabIndexViewModel

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    screens: List<Screen>,
    tabIndexViewModel: TabIndexViewModel = hiltViewModel(),
) {
    val currentRoute = currentRoute(navController = navController)
    val tabIndex by tabIndexViewModel.tabIndex.collectAsState()
    BottomAppBar(
        actions = {
            screens.forEach { screen ->
                NavigationBarItem(
                    icon = {
                        if (screen.route == "stat_screen") {
                            return@NavigationBarItem Icon(
                                imageVector = ImageVector.vectorResource(
                                    id = R.drawable.baseline_query_stats_24
                                ), contentDescription = screen.name
                            )
                        } else {
                            Icon(imageVector = screen.icon!!, contentDescription = screen.name)
                        }
                    },
                    selected = currentRoute == screen.route,
                    onClick = {
                        if (currentRoute != screen.route) {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    if (tabIndex == 0) {
                        navController.navigate(Screen.ExpenseFormScreen.route)
                    } else {
                        navController.navigate(Screen.IncomeFormScreen.route)
                    }
                },
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add icon")
            }
        }
    )
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}