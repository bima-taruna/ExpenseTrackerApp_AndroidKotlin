package com.bima.expensetrackerapp.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.bima.expensetrackerapp.viewmodel.state.ExpensesState

@Composable
fun TransactionList(
    state:ExpensesState,
    modifier: Modifier = Modifier,
) {
    val lazyColumnListState = rememberLazyListState()
    Box {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            state=lazyColumnListState
        ) {
            state.expenses?.size?.let {
                items(it) { i->
                    val expense = state.expenses[i]
                    Row {
                        Text(text = "${expense.name}")
                        Text(text = "${expense.amount}")
                    }
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
        if (state.expenses?.size?.equals(0) == true) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("list is empty")
            }
        }
    }
}