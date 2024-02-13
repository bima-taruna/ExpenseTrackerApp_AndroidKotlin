package com.bima.expensetrackerapp.presentation

import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bima.expensetrackerapp.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onNavigateToAuth : () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val authState by authViewModel.authState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    Text(text = "Profile Screen")
    Button(onClick = {
        coroutineScope.launch {
            authViewModel.signOut(context)
            if (!authState.isSuccess) {
                onNavigateToAuth()
            }
        }
    }) {
        Text("Sign Out")
    }
    if (authState.isLoading) {
        CircularProgressIndicator()
    }
}