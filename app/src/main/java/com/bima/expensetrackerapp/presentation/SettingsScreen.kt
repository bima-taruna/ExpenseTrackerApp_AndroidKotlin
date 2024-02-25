package com.bima.expensetrackerapp.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.bima.expensetrackerapp.presentation.component.settings.SettingsList

@Composable
fun SettingsScreen() {
    SettingsList(list = listOf("Dark Theme", "Currency", "Category"))
}