package com.bima.expensetrackerapp.presentation.component.expense

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bima.expensetrackerapp.viewmodel.CategoryViewModel
import com.bima.expensetrackerapp.viewmodel.expense.ExpenseViewModel
import com.bima.expensetrackerapp.viewmodel.expense.UpdateExpenseViewModel

@Composable
fun UpdateExpense(
    modifier: Modifier = Modifier,
    id: String,
    navController: NavController,
    expenseViewModel: ExpenseViewModel = hiltViewModel(),
    updateExpenseViewModel: UpdateExpenseViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel(),
) {
    val categoryState by categoryViewModel.categoryExpenseState.collectAsStateWithLifecycle()
    val expenseState by expenseViewModel.expenseState.collectAsStateWithLifecycle()
    val updateExpenseState by updateExpenseViewModel.updateExpenseState.collectAsStateWithLifecycle()
    val validationEvent = updateExpenseViewModel.validationEvents


}