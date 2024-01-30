package com.bima.expensetrackerapp.presentation.component.expense

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bima.expensetrackerapp.viewmodel.expense.ExpenseViewModel

@Composable
fun ExpenseDetail(
    id: String,
    expenseViewModel: ExpenseViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by expenseViewModel.expenseState.collectAsStateWithLifecycle()
    LaunchedEffect(
        context
    ) {
        expenseViewModel.getExpenseById(id)
    }
    Log.d("state", state.transaction.toString())
    Column {
        Text(text = "expense detail")
        Text(text = "name : ${state.transaction?.name}")
        Text(text = "amount : ${state.transaction?.amount}")
    }
}