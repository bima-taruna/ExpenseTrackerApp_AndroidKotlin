package com.bima.expensetrackerapp.viewmodel.expense

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bima.expensetrackerapp.ExpenseTrackerApp
import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.data.remote.toExpenses
import com.bima.expensetrackerapp.domain.use_case.expense.GetExpensesUseCase
import com.bima.expensetrackerapp.viewmodel.state.expense.ExpensesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val context: ExpenseTrackerApp,
    private val getExpensesUseCase: GetExpensesUseCase,
) : ViewModel() {
    private val _expensesState = MutableStateFlow(ExpensesState())
    val expensesState = _expensesState.asStateFlow()

    init {
        getExpenses()
    }

    private fun getExpenses() {
        viewModelScope.launch {
            getExpensesUseCase.execute().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _expensesState.value = _expensesState.value.copy(
                            isLoading = false,
                            expenses = result.data?.map {
                                it.toExpenses()
                            }
                        )
                    }

                    is Resource.Error -> {
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                        Log.d("error", result.message.toString())
                        _expensesState.value = _expensesState.value.copy(
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _expensesState.value = _expensesState.value.copy(
                            isLoading = true
                        )
                    }
                }
            }.collect()
        }
    }

}