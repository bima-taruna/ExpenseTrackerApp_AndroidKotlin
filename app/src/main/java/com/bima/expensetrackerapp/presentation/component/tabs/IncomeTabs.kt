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
import com.bima.expensetrackerapp.viewmodel.income.IncomeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun IncomeTabs(
    incomeViewModel: IncomeViewModel = hiltViewModel(),
    balanceViewModel: BalanceViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state by incomeViewModel.incomesState.collectAsStateWithLifecycle()
    val transactionDeleteState by incomeViewModel.deleteIncomeState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(context, transactionDeleteState.transaction) {
        incomeViewModel.getIncomes()
    }
    println(state.transactions?.toString())
    TransactionList(
        transactionsState = state,
        transactionDeleteState = transactionDeleteState,
        isIncome = true,
        navController = navController,
        swipeToDelete = {
            incomeViewModel.deleteIncome(it)
        },
        updateBalance = {
            balanceViewModel.getBalance()
        })
}