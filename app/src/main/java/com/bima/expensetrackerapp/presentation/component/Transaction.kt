package com.bima.expensetrackerapp.presentation.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bima.expensetrackerapp.presentation.component.expense.ExpenseCard
import com.bima.expensetrackerapp.viewmodel.state.expense.TransactionsState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionList(
    state: TransactionsState,
    modifier: Modifier = Modifier,
    isIncome:Boolean
) {
    val lazyColumnListState = rememberLazyListState()
    Box {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            state=lazyColumnListState
        ) {
            state.transactions?.size?.let {
                items(it) { i->
                    val expense = state.transactions[i]
                    ExpenseCard(state = expense, isIncome = isIncome)
                }
            }
        }
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        if (state.transactions?.size?.equals(0) == true) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("list is empty")
            }
        }
    }
}

