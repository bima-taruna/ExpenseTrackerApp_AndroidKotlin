@file:OptIn(ExperimentalMaterial3Api::class)

package com.bima.expensetrackerapp.presentation.component.expense

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bima.expensetrackerapp.presentation.component.transaction.TransactionForm
import com.bima.expensetrackerapp.presentation.component.transaction.TransactionScaffold
import com.bima.expensetrackerapp.viewmodel.category.CategoryViewModel
import com.bima.expensetrackerapp.viewmodel.expense.ExpenseViewModel
import com.bima.expensetrackerapp.viewmodel.expense.UpdateExpenseViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UpdateExpense(
    id: String,
    navController: NavController,
    expenseViewModel: ExpenseViewModel = hiltViewModel(),
    updateExpenseViewModel: UpdateExpenseViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel(),
) {
    val categoryState by categoryViewModel.categoriesExpenseState.collectAsStateWithLifecycle()
    val expenseState by expenseViewModel.expenseState.collectAsStateWithLifecycle()
    val updateExpenseState by updateExpenseViewModel.updateExpenseState.collectAsStateWithLifecycle()
    val validationEvent = updateExpenseViewModel.validationEvents
    val updateFormState by updateExpenseViewModel.updateExpenseFormState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(context) {
        expenseViewModel.getExpenseById(id)
    }
    TransactionScaffold(
        title = "Update Expense",
        backNavigation = {
            navController.popBackStack()
        }) {
        TransactionForm(
            categoriesState = categoryState,
            formState = updateFormState,
            transactionEventState = updateExpenseState,
            id = id,
            isUpdate = true,
            state = expenseState.transaction,
            getCategory = {
                categoryViewModel.getExpenseCategory()
            },
            updateExpense = { id, transaction ->
                updateExpenseViewModel.updateExpense(id, transaction)
            },
            validationEvent = validationEvent,
            onEvent = {
                updateExpenseViewModel.onEvent(it)
            },
            navController = navController
        )
    }
}