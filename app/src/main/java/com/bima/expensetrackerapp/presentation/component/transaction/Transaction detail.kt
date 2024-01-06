package com.bima.expensetrackerapp.presentation.component.transaction

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bima.expensetrackerapp.viewmodel.expense.ExpenseViewModel

@Composable
fun TransactionDetail(
    viewModel: ExpenseViewModel = hiltViewModel(),
    id:String
) {
    Log.d("id", id)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { viewModel.deleteExpense(id) }) {
            Text(text = "Delete")
        }
    }
}