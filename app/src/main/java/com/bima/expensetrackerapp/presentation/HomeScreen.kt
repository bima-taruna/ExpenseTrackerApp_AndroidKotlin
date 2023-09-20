package com.bima.expensetrackerapp.presentation

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.bima.expensetrackerapp.presentation.navigation.Screen
import com.bima.expensetrackerapp.viewmodel.SignInViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val sessionState by viewModel.session.collectAsState()
    Column() {
        Text(text = "Hallo ${sessionState?.user?.email}")
        Button(onClick = {
            coroutineScope.launch(Dispatchers.IO) {
                viewModel.signOut()
                    withContext(Dispatchers.Main) {
                        if (sessionState?.user == null) {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.HomeScreen.route) {
                                    inclusive = true
                                }
                            }
                        }
                    }
            }
        }) {
            Text("Sign Out")
        }
    }
}