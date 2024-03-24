package com.bima.expensetrackerapp.presentation.component.settings.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.bima.expensetrackerapp.presentation.component.tabs.CategoriesTabs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySettings(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                    }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                title = {
                    Text(text = "Category")
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        },
        content = { paddingValues ->
            Box(modifier = modifier.padding(paddingValues)) {
                CategoriesTabs(navController = navController)
            }
        }
    )
}

