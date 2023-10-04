package com.bima.expensetrackerapp.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bima.expensetrackerapp.R
import com.bima.expensetrackerapp.presentation.navigation.Screen
import com.bima.expensetrackerapp.viewmodel.AuthViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val sessionState by viewModel.session.collectAsState()
    val state by viewModel.authState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(sessionState?.user) {
        if (sessionState?.user != null) {
            navController.navigate(Screen.HomeScreen.route) {
                popUpTo(Screen.Login.route) {
                    inclusive = true
                }
            }
            Toast.makeText(context, "Login Berhasil", Toast.LENGTH_LONG).show()
        }
    }

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
                            text = "Sign In",
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
                            localSoftwareKeyboardController?.hide()
                            viewModel.onLogin()

                        }) {
                        Text("Sign in")
                    }
                    OutlinedButton(modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp), onClick = {
//                navController.navigate(SignUpDestination.route)
                    }) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            GlideImage(
                                imageModel = { R.drawable.icons8_google_48 },
                                imageOptions = ImageOptions(
                                    contentScale = ContentScale.Fit
                                ),
                                modifier = modifier.size(24.dp)
                            )
                            Spacer(modifier = modifier.width(8.dp))
                            Text("Sign in with Google")
                        }
                    }
                }
                if (state.error.isNotBlank()) {
                   Toast.makeText(context, "Login gagal", Toast.LENGTH_LONG).show()
                }
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
                }
            }
    }
}