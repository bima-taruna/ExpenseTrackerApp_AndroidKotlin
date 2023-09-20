package com.bima.expensetrackerapp.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bima.expensetrackerapp.presentation.navigation.Screen
import com.bima.expensetrackerapp.viewmodel.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val sessionState by viewModel.session.collectAsState()
    LaunchedEffect(sessionState?.user) {
        if (sessionState == null) {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.HomeScreen.route) {
                    inclusive = true
                }
            }
        }
    }
    Column() {
        Text(text = "Hallo ${sessionState?.user?.email}")
        Button(onClick = {
            coroutineScope.launch {
                viewModel.signOut()
            }
        }) {
            Text("Sign Out")
        }
    }
}