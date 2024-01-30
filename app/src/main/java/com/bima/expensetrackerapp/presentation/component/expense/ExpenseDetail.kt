@file:OptIn(ExperimentalMaterial3Api::class)

package com.bima.expensetrackerapp.presentation.component.expense

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bima.expensetrackerapp.presentation.component.shapes_container.RoundedCornerShapeContainer
import com.bima.expensetrackerapp.presentation.component.transaction.TransactionForm
import com.bima.expensetrackerapp.viewmodel.expense.ExpenseViewModel

@Composable
fun ExpenseDetail(
    id: String,
    modifier: Modifier = Modifier,
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
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
//                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                title = { Text(text = "Expense Detail", fontWeight = FontWeight.SemiBold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        },
        content = { paddingValues ->
            Box(
                modifier = modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                ConstraintLayout {
                    val (container, form) = createRefs()
                    RoundedCornerShapeContainer(
                        modifier = modifier.constrainAs(container) {}
                    ) {}
                    Card(
                        modifier = modifier
                            .fillMaxSize(0.90f)
                            .constrainAs(form) {
                                top.linkTo(container.top, margin = 16.dp)
                                start.linkTo(container.start)
                                end.linkTo(container.end)
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        ),
                    ) {

                    }
                }
            }
        }
    )
}