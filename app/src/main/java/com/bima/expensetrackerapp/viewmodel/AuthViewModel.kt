package com.bima.expensetrackerapp.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bima.expensetrackerapp.ExpenseTrackerApp
import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.common.ValidationEvent
import com.bima.expensetrackerapp.common.form_event.AuthEvent
import com.bima.expensetrackerapp.domain.use_case.auth.GetSessionUseCase
import com.bima.expensetrackerapp.domain.use_case.auth.IsUserLogInUseCase
import com.bima.expensetrackerapp.domain.use_case.auth.SignInUseCase
import com.bima.expensetrackerapp.domain.use_case.auth.SignOutUseCase
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateEmail
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateLoginPassword
import com.bima.expensetrackerapp.viewmodel.state.AuthState
import com.bima.expensetrackerapp.viewmodel.state.LogoutState
import com.bima.expensetrackerapp.viewmodel.state.form.LoginFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.gotrue.user.UserSession
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val context : ExpenseTrackerApp,
    private val signInUseCase: SignInUseCase,
    private val getSessionUseCase: GetSessionUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val validateEmail: ValidateEmail,
    private val validateLoginPassword: ValidateLoginPassword,
    private val isUserLogInUseCase: IsUserLogInUseCase
) : ViewModel() {

    private val _session = MutableStateFlow<UserSession?>(null)
    val session = _session.asStateFlow()
    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()
    private val _loginFormState = MutableStateFlow(LoginFormState())
    val loginFormState = _loginFormState.asStateFlow()
    private val _logoutState = MutableStateFlow(LogoutState())
    val logoutState = _logoutState.asStateFlow()

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()


    init {
        getSession()
    }

    fun onEvent(event: AuthEvent, eventContext: Context = context) {
        when (event) {
            is AuthEvent.EmailChanged -> {
                    _loginFormState.update {
                        it.copy(
                            email = event.email
                        )
                    }
            }
            is AuthEvent.PasswordChanged -> {
                _loginFormState.update {
                    it.copy(
                        password = event.password
                    )
                }
            }
            is AuthEvent.Submit -> {
               submitData()
            }
            is AuthEvent.Login -> {
                onLogin(eventContext)
            }
            is AuthEvent.Logout -> {
                signOut(eventContext)
            }
        }
    }
  private fun onLogin(context: Context) {
        viewModelScope.launch {
            signInUseCase.execute(context = context,email = _loginFormState.value.email,password = _loginFormState.value.password)
                .onEach {result ->
                when(result) {
                    is Resource.Success -> {
                        _session.value = getSessionUseCase.execute()
                        _authState.update {
                            it.copy(
                                isSuccess = result.data!!,
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        Toast.makeText(context,result.message, Toast.LENGTH_SHORT).show()
                        Log.d("result", result.message.toString())
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                isSuccess = false
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _authState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                }
            }.collect()
        }
    }

    private fun getSession() {
        viewModelScope.launch {
            _session.value = getSessionUseCase.execute()
        }
    }


    fun signOut(context: Context) {
        viewModelScope.launch {
            signOutUseCase.execute(context).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _logoutState.update {
                            it.copy(
                                isLogout = result.data ?: false,
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        Toast.makeText(context,result.message, Toast.LENGTH_SHORT).show()
                        _logoutState.update {
                            it.copy(
                                isLoading = false,
                                error = result.message.toString()
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _logoutState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                }
            }.collect()
        }
    }

    fun isUserLogin(
        context: Context
    ) {
        viewModelScope.launch {
            isUserLogInUseCase.execute(context).onEach {result ->
                when(result) {
                    is Resource.Success -> {
                        _authState.update {
                            it.copy(
                                isSuccess = result.data!!,
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        Toast.makeText(context,result.message, Toast.LENGTH_SHORT).show()
                        Log.d("result", result.message.toString())
                        _authState.update {
                            it.copy(
                                isLoading = false,
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _authState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                }
            }.collect()
        }
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(_loginFormState.value.email)
        val passwordResult = validateLoginPassword.execute(_loginFormState.value.password)
        val hasError = listOf(
            emailResult,
            passwordResult
        ).any {
            !it.successful
        }
        if (hasError) {
            _loginFormState.update {
                it.copy(
                    emailError = emailResult.errorMessage,
                    passwordError = passwordResult.errorMessage
                )
            }
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

}