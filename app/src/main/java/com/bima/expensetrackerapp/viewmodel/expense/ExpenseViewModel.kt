package com.bima.expensetrackerapp.viewmodel.expense

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bima.expensetrackerapp.ExpenseTrackerApp
import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.data.remote.toTransactions
import com.bima.expensetrackerapp.domain.use_case.expense.DeleteExpenseUseCase
import com.bima.expensetrackerapp.domain.use_case.expense.GetExpenseByIdUseCase
import com.bima.expensetrackerapp.domain.use_case.expense.GetExpensesUseCase
import com.bima.expensetrackerapp.viewmodel.state.transaction.AddTransactionState
import com.bima.expensetrackerapp.viewmodel.state.transaction.TransactionState
import com.bima.expensetrackerapp.viewmodel.state.transaction.TransactionsState
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
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val getExpenseByIdUseCase: GetExpenseByIdUseCase
) : ViewModel() {
    private val _transactionsState = MutableStateFlow(TransactionsState())
    val expensesState = _transactionsState.asStateFlow()

    private val _transactionState = MutableStateFlow(TransactionState())
    val expenseState = _transactionState.asStateFlow()

    private val _deleteExpenseState = MutableStateFlow(AddTransactionState())
    val deleteExpenseState = _deleteExpenseState.asStateFlow();

    fun getExpenses() {
        viewModelScope.launch {
            getExpensesUseCase.execute().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _transactionsState.value = _transactionsState.value.copy(
                            isLoading = false,
                            transactions = result.data?.map {
                                it.toTransactions()
                            }
                        )
                    }

                    is Resource.Error -> {
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                        Log.d("error", result.message.toString())
                        _transactionsState.value = _transactionsState.value.copy(
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _transactionsState.value = _transactionsState.value.copy(
                            isLoading = true
                        )
                    }
                }
            }.collect()
        }
    }

    fun getExpenseById(id: String) {
        viewModelScope.launch {
            getExpenseByIdUseCase.execute(id).onEach { result->
                when(result){
                    is Resource.Success -> {
                        Log.d("from id", "Successs")
                        _transactionState.value = _transactionState.value.copy(
                            isLoading = false,
                            transaction = result.data
                        )
                    }
                    is Resource.Error -> {
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                        Log.d("error", result.message.toString())
                        _transactionState.value = _transactionState.value.copy(
                            isLoading = false,
                        )
                    }

                    is Resource.Loading -> {
                        _transactionState.value = _transactionState.value.copy(
                            isLoading = true
                        )
                    }
                }
            }.collect()
        }
    }



    fun deleteExpense(id: String) {
        viewModelScope.launch {
            deleteExpenseUseCase.execute(id).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _deleteExpenseState.value = _deleteExpenseState.value.copy(
                            isLoading = false,
                            transaction = true
                        )
                        getExpenses()
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Error -> {
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                        Log.d("error", result.message.toString())
                        _deleteExpenseState.value = _deleteExpenseState.value.copy(
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        _deleteExpenseState.value = _deleteExpenseState.value.copy(
                            isLoading = true
                        )
                    }
                }
            }.collect()
        }
    }

}