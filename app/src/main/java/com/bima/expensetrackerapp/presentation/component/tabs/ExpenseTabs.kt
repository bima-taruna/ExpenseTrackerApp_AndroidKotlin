package com.bima.expensetrackerapp.presentation.component.tabs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bima.expensetrackerapp.presentation.component.TransactionList
import com.bima.expensetrackerapp.viewmodel.expense.ExpenseViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpenseTabs(
    expenseViewModel: ExpenseViewModel = hiltViewModel(),
) {
    val state by expenseViewModel.expensesState.collectAsStateWithLifecycle()
    println(state.transactions?.toString())
    TransactionList(state = state, isIncome = false)
}