package com.bima.expensetrackerapp.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.bima.expensetrackerapp.presentation.component.settings.SettingsList

@Composable
fun SettingsScreen(
    navController: NavController
) {
    SettingsList(list = listOf("Dark Theme", "Currency", "Category"), navController = navController)
}