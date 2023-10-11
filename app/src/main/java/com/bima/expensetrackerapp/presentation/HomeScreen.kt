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
import com.bima.expensetrackerapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val sessionState by authViewModel.session.collectAsState()
    val userState by userViewModel.userState.collectAsState()
    LaunchedEffect(sessionState?.user) {
        if (sessionState == null) {
            navController.navigate(Screen.Title.route) {
                popUpTo(Screen.HomeScreen.route) {
                    inclusive = true
                }
            }
        }
    }
    Column() {
        Text(text = "Hallo ${userState.user?.name}")
        Button(onClick = {
            coroutineScope.launch {
                authViewModel.signOut()
            }
        }) {
            Text("Sign Out")
        }
    }
}