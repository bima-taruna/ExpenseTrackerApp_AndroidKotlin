package com.bima.expensetrackerapp.presentation.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bima.expensetrackerapp.R
import com.bima.expensetrackerapp.common.ValidationEvent
import com.bima.expensetrackerapp.common.form_event.AuthEvent
import com.bima.expensetrackerapp.presentation.navigation.Graph
import com.bima.expensetrackerapp.viewmodel.AuthViewModel
import com.bima.expensetrackerapp.viewmodel.state.AuthState
import com.bima.expensetrackerapp.viewmodel.state.form.LoginFormState
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    state:AuthState? = null,
    onEvent: (AuthEvent, Context) -> Unit,
    validationEvent: Flow<ValidationEvent>? = null,
    navigateBack : () -> Unit,
    context: Context,
    formState:LoginFormState? = null
) {
    LaunchedEffect(key1 = context) {
        validationEvent?.collect {event->
            when(event) {
                is ValidationEvent.Success -> {
                    onEvent(AuthEvent.Login, context)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = SnackbarHostState()) },
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navigateBack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {},
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
                val passwordVisible by rememberSaveable { mutableStateOf(false) }
                Text(text = "Welcome Back!", style = MaterialTheme.typography.headlineLarge, modifier = modifier.padding(bottom = 12.dp))
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
                    value = formState?.email ?: "",
                    onValueChange = {
                        onEvent(AuthEvent.EmailChanged(it), context)
                    },
                    isError = formState?.emailError != null,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    )
                )
                if (formState?.emailError != null) {
                    Text(text = formState.emailError.asString(context), color = MaterialTheme.colorScheme.error)
                }
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
                    value = formState?.password ?: "",
                    onValueChange = {
                        onEvent(AuthEvent.PasswordChanged(it), context)
                    },
                    isError = formState?.passwordError != null,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                )
                if (formState?.passwordError != null) {
                    Text(text = formState.passwordError.asString(context), color = MaterialTheme.colorScheme.error)
                }
                Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "Forgot password?")
                    }
                }
                val localSoftwareKeyboardController = LocalSoftwareKeyboardController.current
                Button(modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 8.dp),
                    onClick = {
                        localSoftwareKeyboardController?.hide()
                        onEvent(AuthEvent.Submit, context)
                    }) {
                    Text("Sign in")
                }
                Text(text = "Or login with", textAlign = TextAlign.Center, style = MaterialTheme.typography.labelLarge, modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp))
                Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    IconButton(onClick = { /*TODO*/ },
                        modifier
                            .size(60.dp)
                            .border(
                                1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(10.dp)
                            )) {
                            GlideImage(
                                imageModel = { R.drawable.icons8_google_48 },
                                imageOptions = ImageOptions(
                                    contentScale = ContentScale.Fit
                                ),
                                previewPlaceholder = painterResource(id = R.drawable.icons8_google_48) ,
                                modifier = modifier.size(36.dp)
                            )
                    }
                }
            }
            if (state != null) {
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
                }
            }
        }
    }
}

@Composable
@Preview
fun LoginScreenPreview() {
    val context = LocalContext.current
    LoginScreen(
        state = null,
        onEvent = {authEvent, context ->  },
        validationEvent = null,
        navigateBack = { /*TODO*/ },
        context = context,
        formState = null
    )
}