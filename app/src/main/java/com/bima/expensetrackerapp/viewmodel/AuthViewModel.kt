package com.bima.expensetrackerapp.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bima.expensetrackerapp.ExpenseTrackerApp
import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.domain.repository.AuthenticationRepository
import com.bima.expensetrackerapp.viewmodel.state.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.gotrue.user.UserSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val context : ExpenseTrackerApp,
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: Flow<String> = _email
    private val _password = MutableStateFlow("")
    val password = _password
    private val _session = MutableStateFlow<UserSession?>(null)
    val session = _session
    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState

    init {
        getSession()

        Log.d("viewmodel", session.value?.user.toString())
    }

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onLogin() {
        authenticationRepository.signIn(email = _email.value, password = _password.value)
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _session.value = authenticationRepository.getSession()
                        _authState.value = _authState.value.copy(
                            isSuccess = true
                        )
                        Log.d("statefromviewmodel", authState.value.toString())
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
            }.launchIn(viewModelScope)
    }

    private fun getSession() {
        viewModelScope.launch {
            _session.value = authenticationRepository.getSession()
        }
    }

    suspend fun signOut() {
        try {
            authenticationRepository.signOut()
            _session.value = null
        } catch (e: Exception) {
            Log.d("Error", e.message.toString())
        }
    }

}