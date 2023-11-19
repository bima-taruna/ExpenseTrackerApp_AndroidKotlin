package com.bima.expensetrackerapp.presentation

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bima.expensetrackerapp.presentation.navigation.Graph
import com.bima.expensetrackerapp.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val sessionState by authViewModel.session.collectAsStateWithLifecycle()
    LaunchedEffect(sessionState?.user) {
        if (sessionState == null) {
            navController.navigate(Graph.AUTH) {
                popUpTo(0) {
                    inclusive = true
                }
            }
        }
    }
    Text(text = "Profile Screen")
    Button(onClick = {
        coroutineScope.launch {
            authViewModel.signOut()
        }
    }) {
        Text("Sign Out")
    }
}