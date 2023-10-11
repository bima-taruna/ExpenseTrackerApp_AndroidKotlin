package com.bima.expensetrackerapp.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bima.expensetrackerapp.ExpenseTrackerApp
import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.domain.use_case.auth.GetSessionUseCase
import com.bima.expensetrackerapp.domain.use_case.user.GetProfileUseCase
import com.bima.expensetrackerapp.viewmodel.state.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.gotrue.user.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val getSessionUseCase: GetSessionUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val context : ExpenseTrackerApp
):ViewModel() {
    private val _session = MutableStateFlow<UserSession?>(null)
    val session = _session

    private val _userState = MutableStateFlow(UserState())
    val userState = _userState



    init {
        getSession()
        getUserProfile()
        Log.d("id", session.value?.user.toString())
        Log.d("user", userState.value.error)

    }

    private fun getUserProfile() {
        viewModelScope.launch {
            getProfileUseCase.execute(session.value?.user?.id).onEach {result->
                when(result) {
                    is Resource.Success -> {
                        _userState.value = _userState.value.copy(
                            user = result.data
                        )
                    }
                    is Resource.Error -> {
                        Toast.makeText(context,result.message, Toast.LENGTH_SHORT).show()
                        _userState.value = _userState.value.copy(
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        _userState.value = _userState.value.copy(
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
}