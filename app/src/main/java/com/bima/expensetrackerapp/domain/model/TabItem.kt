package com.bima.expensetrackerapp.domain.model

import androidx.compose.runtime.Composable

data class TabItem(
    val title: String,
    val screen: @Composable () -> Unit
)
