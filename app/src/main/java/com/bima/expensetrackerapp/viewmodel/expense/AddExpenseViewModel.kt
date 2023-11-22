package com.bima.expensetrackerapp.viewmodel.expense

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bima.expensetrackerapp.ExpenseTrackerApp
import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.domain.model.Expense
import com.bima.expensetrackerapp.domain.use_case.expense.CreateExpenseUseCase
import com.bima.expensetrackerapp.viewmodel.state.expense.AddExpenseState
import com.bima.expensetrackerapp.viewmodel.state.expense.ExpensesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val context: ExpenseTrackerApp,
    private val createExpenseUseCase: CreateExpenseUseCase,
) : ViewModel() {
    private val _addExpenseState = MutableStateFlow(AddExpenseState())
    val addExpenseState = _addExpenseState.asStateFlow()

    fun createExpense(input:Expense) {
        viewModelScope.launch {
            createExpenseUseCase.execute(input).onEach {result->
                when(result) {
                    is Resource.Success -> {
                        _addExpenseState.value = _addExpenseState.value.copy(
                            isLoading = false,
                            expenses = true
                        )
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Error -> {
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                        Log.d("error", result.message.toString())
                        _addExpenseState.value = _addExpenseState.value.copy(
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        _addExpenseState.value = _addExpenseState.value.copy(
                            isLoading = true
                        )
                    }
                }
            }.collect()
        }
    }
}