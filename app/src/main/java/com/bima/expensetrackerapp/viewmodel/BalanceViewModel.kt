package com.bima.expensetrackerapp.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bima.expensetrackerapp.ExpenseTrackerApp
import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.common.ValidationEvent
import com.bima.expensetrackerapp.common.form_event.BalanceFormEvent
import com.bima.expensetrackerapp.domain.use_case.balance.GetBalanceUseCase
import com.bima.expensetrackerapp.domain.use_case.balance.UpdateBalanceUseCase
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateAmount
import com.bima.expensetrackerapp.viewmodel.state.BalanceState
import com.bima.expensetrackerapp.viewmodel.state.form.BalanceFormState
import com.bima.expensetrackerapp.viewmodel.state.form.TransactionFormState
import com.bima.expensetrackerapp.viewmodel.state.transaction.EventTransactionState
import dagger.hilt.android.lifecycle.HiltViewModel
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
class BalanceViewModel @Inject constructor(
    private val context : ExpenseTrackerApp,
    private val getBalanceUseCase: GetBalanceUseCase,
    private val updateBalanceUseCase: UpdateBalanceUseCase,
    private val validateAmount: ValidateAmount
):ViewModel() {
    private val _balanceState = MutableStateFlow(BalanceState())
    val balanceState = _balanceState.asStateFlow()

    private val _updateBalanceState = MutableStateFlow(EventTransactionState())
    val updateBalanceState = _updateBalanceState.asStateFlow()

    private val _updateBalanceFormState = MutableStateFlow(BalanceFormState())
    val updateBalanceFormState = _updateBalanceFormState.asStateFlow()

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: BalanceFormEvent) {
        when(event) {
            is BalanceFormEvent.AmountChanged -> {
                _updateBalanceFormState.update {
                    it.copy(
                        amount = event.amount
                    )
                }
            }
            is BalanceFormEvent.Submit -> {
                submitData()
            }
        }
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

    fun updateBalance(userId:String, data:Int) {
        viewModelScope.launch {
            updateBalanceUseCase.execute(userId, data).onEach { result->
                when(result) {
                    is Resource.Success -> {
                        _updateBalanceState.update {
                            it.copy(transaction = result.data ?: false, isLoading = false)
                        }
                        Toast.makeText(context,"Success", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Error -> {
                        _updateBalanceState.update {
                            it.copy(isLoading = false, error = result.message.toString())
                        }
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        _updateBalanceState.update {
                            it.copy(isLoading = true)
                        }
                    }
                }
            }.collect()
        }
    }

    private fun submitData() {
        val amountResult = validateAmount.execute(_updateBalanceFormState.value.amount)
        val hasError = !amountResult.successful
        if (hasError) {
            _updateBalanceFormState.update {
                it.copy(
                    amountError = amountResult.errorMessage
                )
            }
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }
}