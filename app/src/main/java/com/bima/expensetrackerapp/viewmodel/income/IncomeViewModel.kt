package com.bima.expensetrackerapp.viewmodel.income

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bima.expensetrackerapp.ExpenseTrackerApp
import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.data.remote.toTransactions
import com.bima.expensetrackerapp.domain.use_case.income.GetIncomesUseCase
import com.bima.expensetrackerapp.viewmodel.state.expense.TransactionsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncomeViewModel @Inject constructor(
    private val context: ExpenseTrackerApp,
    private val getIncomeUseCase: GetIncomesUseCase,
) : ViewModel() {
    private val _incomesState = MutableStateFlow(TransactionsState())
    val incomesState = _incomesState.asStateFlow()

    init {
        getIncomes()
    }

    private fun getIncomes() {
        viewModelScope.launch {
            getIncomeUseCase.execute().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _incomesState.value = _incomesState.value.copy(
                            isLoading = false,
                            transactions = result.data?.map {
                                it.toTransactions()
                            }
                        )
                    }

                    is Resource.Error -> {
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                        Log.d("error", result.message.toString())
                        _incomesState.value = _incomesState.value.copy(
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _incomesState.value = _incomesState.value.copy(
                            isLoading = true
                        )
                    }
                }
            }.collect()
        }
    }

}