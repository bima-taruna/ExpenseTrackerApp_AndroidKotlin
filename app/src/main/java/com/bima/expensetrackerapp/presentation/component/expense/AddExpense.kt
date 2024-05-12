package com.bima.expensetrackerapp.presentation.component.expense

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bima.expensetrackerapp.presentation.component.transaction.TransactionForm
import com.bima.expensetrackerapp.presentation.component.transaction.TransactionScaffold
import com.bima.expensetrackerapp.viewmodel.category.CategoryViewModel
import com.bima.expensetrackerapp.viewmodel.expense.AddExpenseViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpense(
    modifier: Modifier = Modifier,
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    addExpenseViewModel: AddExpenseViewModel = hiltViewModel(),
    navController: NavController,
) {
    val categoryState by categoryViewModel.categoriesExpenseState.collectAsStateWithLifecycle()
    val formState by addExpenseViewModel.expenseFormState.collectAsStateWithLifecycle()
    val addExpenseState by addExpenseViewModel.addExpenseState.collectAsStateWithLifecycle()
    val validationEvent = addExpenseViewModel.validationEvents

    TransactionScaffold(
        title = "Add Expense",
        hasAction = false,
        backNavigation = {
            navController.popBackStack()
        }) {
        TransactionForm(
            modifier.fillMaxSize(),
            navController = navController,
            transactionEventState = addExpenseState,
            categoriesState = categoryState,
            formState = formState,
            validationEvent = validationEvent,
            createExpense = {
                addExpenseViewModel.createExpense(it)
            },
            getCategory = {
                categoryViewModel.getExpenseCategory()
            },
            onEvent = {
                addExpenseViewModel.onEvent(it)
            },
        )
    }
}