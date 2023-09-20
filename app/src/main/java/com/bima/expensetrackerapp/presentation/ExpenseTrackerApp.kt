package com.bima.expensetrackerapp.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.bima.expensetrackerapp.presentation.navigation.Navigation

@Composable
fun ExpenseTrackerApp() {
    val navController = rememberNavController()
    Box(modifier = Modifier.fillMaxSize()){
        Navigation(navController = navController)
    }
}