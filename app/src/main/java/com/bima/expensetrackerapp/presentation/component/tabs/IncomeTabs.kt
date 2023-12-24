package com.bima.expensetrackerapp.presentation.component.tabs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bima.expensetrackerapp.presentation.component.TransactionList
import com.bima.expensetrackerapp.viewmodel.income.IncomeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun IncomeTabs(
    incomeViewModel: IncomeViewModel = hiltViewModel(),
) {
    val state by incomeViewModel.incomesState.collectAsStateWithLifecycle()
    println(state.transactions?.toString())
    TransactionList(state = state, isIncome = true)
}