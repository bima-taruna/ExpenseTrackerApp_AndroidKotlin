@file:OptIn(ExperimentalMaterial3Api::class)

package com.bima.expensetrackerapp.presentation.component.income

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
import com.bima.expensetrackerapp.viewmodel.income.IncomeViewModel
import com.bima.expensetrackerapp.viewmodel.income.UpdateIncomeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UpdateIncome(
    id: String,
    navController: NavController,
    incomeViewModel: IncomeViewModel = hiltViewModel(),
    updateIncomeViewModel: UpdateIncomeViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel(),
) {
    val categoryState by categoryViewModel.categoryExpenseState.collectAsStateWithLifecycle()
    val incomeState by incomeViewModel.incomeState.collectAsStateWithLifecycle()
    val updateIncomeState by updateIncomeViewModel.updateIncomeState.collectAsStateWithLifecycle()
    val validationEvent = updateIncomeViewModel.validationEvents
    val context = LocalContext.current
    val updateFormState by updateIncomeViewModel.updateIncomeFormState.collectAsStateWithLifecycle()
    LaunchedEffect(context) {
        incomeViewModel.getIncomeById(id)
    }
    TransactionScaffold(
        title = "Update Income",
        backNavigation = {
            navController.popBackStack()
        }) {
        TransactionForm(
            categoryState = categoryState,
            formState = updateFormState,
            transactionEventState = updateIncomeState,
            id = id,
            isUpdate = true,
            state = incomeState.transaction,
            getCategory = {
                categoryViewModel.getIncomeCategory()
            },
            updateExpense = { id, transaction ->
                updateIncomeViewModel.updateIncome(id, transaction)
            },
            validationEvent = validationEvent,
            onEvent = {
                updateIncomeViewModel.onEvent(it)
            },
            navController = navController
        )
    }
}