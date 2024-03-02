package com.bima.expensetrackerapp.presentation

import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bima.expensetrackerapp.common.form_event.AuthEvent
import com.bima.expensetrackerapp.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onNavigateToAuth : () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val logoutState by authViewModel.logoutState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(logoutState.isLogout) {
        if (logoutState.isLogout) {
            onNavigateToAuth()
        }
    }
    Text(text = "Profile Screen")
    Button(onClick = {
            authViewModel.onEvent(AuthEvent.Logout, context)
    }) {
        Text("Sign Out")
    }
    if (logoutState.isLoading) {
        CircularProgressIndicator()
    }
}