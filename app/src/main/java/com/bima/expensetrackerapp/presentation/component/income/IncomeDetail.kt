@file:OptIn(ExperimentalMaterial3Api::class)

package com.bima.expensetrackerapp.presentation.component.income

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bima.expensetrackerapp.presentation.component.transaction.TransactionDetail
import com.bima.expensetrackerapp.presentation.component.transaction.TransactionScaffold
import com.bima.expensetrackerapp.presentation.navigation.Graph
import com.bima.expensetrackerapp.viewmodel.income.IncomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun IncomeDetail(
    id: String,
    modifier: Modifier = Modifier,
    incomeViewModel: IncomeViewModel = hiltViewModel(),
    navController: NavController,
) {
    val context = LocalContext.current
    val state by incomeViewModel.incomeState.collectAsStateWithLifecycle()
    val composableScope = rememberCoroutineScope()
    LaunchedEffect(
        context
    ) {
        incomeViewModel.getIncomeById(id)
    }
    TransactionScaffold(
        title = "Income Detail",
        hasAction = true,
        backNavigation = {
            navController.popBackStack()
        },
        delete = {
            composableScope.launch {
                incomeViewModel.deleteIncome(id)
                delay(1000)
                navController.navigate(Graph.MAIN) {
                    popUpTo(Graph.MAIN) {
                        inclusive = true
                    }
                }
            }
        }
    ) {
        TransactionDetail(state = state, isIncome = true)
    }
}

