package com.bima.expensetrackerapp.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bima.expensetrackerapp.ExpenseTrackerApp
import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.domain.use_case.auth.GetSessionUseCase
import com.bima.expensetrackerapp.domain.use_case.auth.SignInUseCase
import com.bima.expensetrackerapp.domain.use_case.auth.SignOutUseCase
import com.bima.expensetrackerapp.viewmodel.state.AuthState
import com.bima.expensetrackerapp.viewmodel.state.form.LoginFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.gotrue.user.UserSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val context : ExpenseTrackerApp,
    private val signInUseCase: SignInUseCase,
    private val getSessionUseCase: GetSessionUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: Flow<String> = _email
    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()
    private val _session = MutableStateFlow<UserSession?>(null)
    val session = _session.asStateFlow()
    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()
    private val _loginFormState = MutableStateFlow(LoginFormState())
    val loginFormState = _loginFormState.asStateFlow()

    init {
        getSession()
    }

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

  fun onLogin() {
        viewModelScope.launch {
            signInUseCase.execute(email = _email.value,password = _password.value)
                .onEach {result ->
                when(result) {
                    is Resource.Success -> {
                        _session.value = getSessionUseCase.execute()
                        _authState.value = _authState.value.copy(
                            isSuccess = true
                        )
                    }
                    is Resource.Error -> {
                        Toast.makeText(context,result.message, Toast.LENGTH_SHORT).show()
                        _authState.value = _authState.value.copy(
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        _authState.value = _authState.value.copy(
                            isLoading = true
                        )
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

    suspend fun signOut() {
        try {
            signOutUseCase.execute()
            _session.value = null
        } catch (e: Exception) {
            Log.d("Error", e.message.toString())
        }
    }

}