package com.bima.expensetrackerapp.presentation.component.tabs

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bima.expensetrackerapp.presentation.component.TransactionList
import com.bima.expensetrackerapp.viewmodel.ExpenseViewModel

@Composable
fun ExpenseTabs(
    expenseViewModel: ExpenseViewModel = hiltViewModel(),
) {
    val state by expenseViewModel.expensesState.collectAsStateWithLifecycle()
    println(state.expenses?.toString())
    TransactionList(state = state)
}