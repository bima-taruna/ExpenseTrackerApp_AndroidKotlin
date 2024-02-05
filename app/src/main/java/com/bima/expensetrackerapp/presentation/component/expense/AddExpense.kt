package com.bima.expensetrackerapp.presentation.component.expense

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
import com.bima.expensetrackerapp.presentation.component.shapes_container.RoundedCornerShapeContainer
import com.bima.expensetrackerapp.presentation.component.transaction.TransactionForm
import com.bima.expensetrackerapp.presentation.component.transaction.TransactionScaffold
import com.bima.expensetrackerapp.viewmodel.CategoryViewModel
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
    val categoryState by categoryViewModel.categoryExpenseState.collectAsStateWithLifecycle()
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
            addExpenseState = addExpenseState,
            categoryState = categoryState,
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
            }
        )
    }
}