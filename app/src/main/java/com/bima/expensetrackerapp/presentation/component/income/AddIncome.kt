package com.bima.expensetrackerapp.presentation.component.income

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bima.expensetrackerapp.presentation.component.transaction.TransactionForm
import com.bima.expensetrackerapp.presentation.component.shapes_container.RoundedCornerShapeContainer
import com.bima.expensetrackerapp.viewmodel.CategoryViewModel
import com.bima.expensetrackerapp.viewmodel.income.AddIncomeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIncome(
    modifier: Modifier = Modifier,
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    addIncomeViewModel: AddIncomeViewModel = hiltViewModel(),
    navController: NavController,
) {
    val categoryState by categoryViewModel.categoryIncomeState.collectAsStateWithLifecycle()
    val formState by addIncomeViewModel.incomeFormState.collectAsStateWithLifecycle()
    val addExpenseState by addIncomeViewModel.addIncomeState.collectAsStateWithLifecycle()

    val validationEvent = addIncomeViewModel.validationEvents

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                title = { Text(text = "Add Income", fontWeight = FontWeight.SemiBold) },
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
                        TransactionForm(
                            modifier.fillMaxSize(),
                            navController = navController,
                            addExpenseState = addExpenseState,
                            categoryState = categoryState,
                            formState = formState,
                            validationEvent = validationEvent,
                            createExpense = {
                                addIncomeViewModel.createIncome(it)
                            },
                            getCategory = {
                                categoryViewModel.getIncomeCategory()
                            },
                            onEvent = {
                                addIncomeViewModel.onEvent(it)
                            }
                        )
                    }
                }
            }
        }
    )
}