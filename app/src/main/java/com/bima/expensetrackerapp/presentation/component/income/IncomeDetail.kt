@file:OptIn(ExperimentalMaterial3Api::class)

package com.bima.expensetrackerapp.presentation.component.income

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bima.expensetrackerapp.presentation.component.shapes_container.RoundedCornerShapeContainer
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

