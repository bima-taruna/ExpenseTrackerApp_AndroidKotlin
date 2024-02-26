package com.bima.expensetrackerapp.presentation.login

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bima.expensetrackerapp.presentation.navigation.Graph
import com.bima.expensetrackerapp.viewmodel.AuthViewModel

@Composable
fun LoginScreenRoot(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val state by authViewModel.authState.collectAsState()
    val formState by authViewModel.loginFormState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            navController.popBackStack()
            navController.navigate(Graph.MAIN)
            Toast.makeText(context, "Login Berhasil", Toast.LENGTH_LONG).show()
        }
    }
    LoginScreen(
        state = state,
        onEvent = authViewModel::onEvent,
        validationEvent = authViewModel.validationEvents,
        navigateBack = {
            navController.navigateUp()
        },
        context = context,
        formState = formState
    )
}



