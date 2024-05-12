package com.bima.expensetrackerapp.presentation.component.income

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
    val categoryState by categoryViewModel.categoriesIncomeState.collectAsStateWithLifecycle()
    val formState by addIncomeViewModel.incomeFormState.collectAsStateWithLifecycle()
    val addExpenseState by addIncomeViewModel.addIncomeState.collectAsStateWithLifecycle()
    val validationEvent = addIncomeViewModel.validationEvents

    TransactionScaffold(
        title = "Add Income",
        hasAction = false,
        backNavigation = {
            navController.popBackStack()
        }
    ) {
        TransactionForm(
            modifier.fillMaxSize(),
            navController = navController,
            transactionEventState = addExpenseState,
            categoriesState = categoryState,
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