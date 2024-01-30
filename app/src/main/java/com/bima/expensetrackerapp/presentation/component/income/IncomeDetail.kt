package com.bima.expensetrackerapp.presentation.component.income

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bima.expensetrackerapp.viewmodel.expense.ExpenseViewModel
import com.bima.expensetrackerapp.viewmodel.income.IncomeViewModel

@Composable
fun IncomeDetail(
    id: String,
    incomeViewModel: IncomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by incomeViewModel.incomeState.collectAsStateWithLifecycle()
    LaunchedEffect(
        state.transaction
    ) {
        incomeViewModel.getIncomeById(id)
    }
    Column {
        Text(text = "income detail")
        Text(text = "nama : ${state.transaction?.name}")
        Text(text = "nama : ${state.transaction?.amount}")
    }
}