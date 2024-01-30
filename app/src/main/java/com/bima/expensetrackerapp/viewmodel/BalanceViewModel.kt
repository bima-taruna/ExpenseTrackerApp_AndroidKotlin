package com.bima.expensetrackerapp.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bima.expensetrackerapp.ExpenseTrackerApp
import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.domain.use_case.balance.GetBalanceUseCase
import com.bima.expensetrackerapp.viewmodel.state.BalanceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BalanceViewModel @Inject constructor(
    private val context : ExpenseTrackerApp,
    private val getBalanceUseCase: GetBalanceUseCase
):ViewModel() {
    private val _balanceState = MutableStateFlow(BalanceState())
    val balanceState = _balanceState.asStateFlow()

    init {
       getBalance()
    }

    fun getBalance() {
        viewModelScope.launch {
            getBalanceUseCase.execute().onEach { result->
                when(result) {
                    is Resource.Success -> {
                        _balanceState.update {
                            it.copy(
                                isLoading = false,
                                balance = result.data
                            )
                        }
                    }
                    is Resource.Error -> {
                        Toast.makeText(context,result.message, Toast.LENGTH_SHORT).show()
                        Log.d("error", result.message.toString())
                        _balanceState.update {
                            it.copy(isLoading = false)
                        }
                    }
                    is Resource.Loading -> {
                        _balanceState.update {
                            it.copy(isLoading = true)
                        }
                    }
                }
            }.collect()
        }
    }
}