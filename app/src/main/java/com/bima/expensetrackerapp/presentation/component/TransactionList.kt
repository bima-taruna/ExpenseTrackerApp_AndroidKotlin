package com.bima.expensetrackerapp.presentation.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bima.expensetrackerapp.presentation.component.transaction.TransactionCard
import com.bima.expensetrackerapp.presentation.navigation.Screen
import com.bima.expensetrackerapp.viewmodel.state.transaction.EventTransactionState
import com.bima.expensetrackerapp.viewmodel.state.transaction.TransactionsState
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionList(
    transactionsState: TransactionsState,
    transactionDeleteState:EventTransactionState,
    modifier: Modifier = Modifier,
    isIncome:Boolean,
    navController: NavController,
    swipeToDelete:(id:String)->Unit,
    updateBalance:()->Unit
) {
    val lazyColumnListState = rememberLazyListState()
    val isThisIncome: (String) -> String = {id->
        if (isIncome) {
            Screen.IncomeDetailScreen.route + id
        } else {
            Screen.ExpenseDetailScreen.route + id
        }
    }
    LaunchedEffect(transactionDeleteState.transaction) {
        if (transactionDeleteState.transaction) {
            updateBalance()
        }
    }
    Box {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            state=lazyColumnListState
        ) {
            transactionsState.transactions?.let {
                items(items = it, key = { transaction->
                    transaction.id ?: ""
                }) {
                    val delete = SwipeAction(
                        onSwipe = {
                            it.id?.let { id -> swipeToDelete(id) }
                        },
                        icon = {
                            Icon(imageVector = Icons.Filled.Delete, contentDescription = "delete expense")
                        }, background = Color.Red, isUndo = true
                    )
                    SwipeableActionsBox(
                        modifier = Modifier,
                        swipeThreshold = 200.dp,
                        endActions = listOf(delete)
                    ) {
                        TransactionCard(state = it, isIncome = isIncome, navigateToDetail = {
                            navController.navigate(
                                isThisIncome(it.id ?: "")
                            )
                        })
                    }
                }
            }
        }
        if (transactionsState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        if (transactionsState.transactions?.size?.equals(0) == true) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("list is empty")
            }
        }
    }
}

