package com.bima.expensetrackerapp.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bima.expensetrackerapp.presentation.navigation.Screen
import com.bima.expensetrackerapp.viewmodel.SignInViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val sessionState by viewModel.session.collectAsState()
    val state by viewModel.authState.collectAsState()
    LaunchedEffect(sessionState?.user) {
        if (sessionState?.user != null && state.isSuccess) {
            navController.navigate(Screen.HomeScreen.route) {
                popUpTo(Screen.Login.route) {
                    inclusive = true
                }
            }
        }
    }
    if (sessionState == null) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = SnackbarHostState()) },
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.navigateUp()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                    title = {
                        Text(
                            text = "Login",
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    },
                )
            }
        ) { paddingValues ->
            Box(
                modifier = modifier.padding(paddingValues)
            ) {
                Column(
                    modifier = modifier
                        .padding(20.dp)
                ) {
                    val email = viewModel.email.collectAsState(initial = "")
                    val password = viewModel.password.collectAsState()
                    OutlinedTextField(
                        label = {
                            Text(
                                text = "Email",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        maxLines = 1,
                        shape = RoundedCornerShape(32),
                        modifier = modifier.fillMaxWidth(),
                        value = email.value,
                        onValueChange = {
                            viewModel.onEmailChange(it)
                        },
                    )
                    OutlinedTextField(
                        label = {
                            Text(
                                text = "Password",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        maxLines = 1,
                        shape = RoundedCornerShape(32),
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        value = password.value,
                        onValueChange = {
                            viewModel.onPasswordChange(it)
                        },
                    )
                    val localSoftwareKeyboardController = LocalSoftwareKeyboardController.current
                    Button(modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                        onClick = {

                        }) {
                        Text("Sign in with Google")
                    }
                    Button(modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                        onClick = {
                            localSoftwareKeyboardController?.hide()
                            viewModel.onLogin()
                            Log.d("session", sessionState?.user.toString())


                            Log.d("state", state.toString())
                            Log.d("sessionState.value", sessionState?.user.toString())
                        }) {
                        Text("Sign in")
                    }
                    OutlinedButton(modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp), onClick = {
//                navController.navigate(SignUpDestination.route)
                    }) {
                        Text("Sign up")
                    }
                }
                if (state.error.isNotBlank()) {
                    Text(
                        text = state.error,
                        textAlign = TextAlign.Justify,
                        modifier = modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                    )
                }
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
                }
            }
        }
    }
}