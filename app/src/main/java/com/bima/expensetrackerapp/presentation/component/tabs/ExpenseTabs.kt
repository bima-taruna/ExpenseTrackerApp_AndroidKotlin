package com.bima.expensetrackerapp.presentation.component.tabs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bima.expensetrackerapp.presentation.component.TransactionList
import com.bima.expensetrackerapp.viewmodel.BalanceViewModel
import com.bima.expensetrackerapp.viewmodel.expense.ExpenseViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpenseTabs(
    expenseViewModel: ExpenseViewModel = hiltViewModel(),
    balanceViewModel: BalanceViewModel = hiltViewModel(),
    navController: NavController,
) {
    val context = LocalContext.current
    val state by expenseViewModel.expensesState.collectAsStateWithLifecycle()
    LaunchedEffect(context) {
        expenseViewModel.getExpenses()
    }
    println(state.transactions?.toString())
    TransactionList(
        state = state,
        isIncome = false,
        navController = navController,
        swipeToDelete = {
            expenseViewModel.deleteExpense(it)
        },
        updateBalance = {
            balanceViewModel.getBalance()
        }
    )
}