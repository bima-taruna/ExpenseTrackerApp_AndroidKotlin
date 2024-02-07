package com.bima.expensetrackerapp.presentation.component.expense

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
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
    
}