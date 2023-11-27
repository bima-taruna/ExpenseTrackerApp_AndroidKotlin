package com.bima.expensetrackerapp.presentation.component.expense

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bima.expensetrackerapp.domain.model.Expense
import com.bima.expensetrackerapp.presentation.component.form.CurrencyTextField
import com.bima.expensetrackerapp.presentation.component.form.Dropdown
import com.bima.expensetrackerapp.presentation.navigation.Graph
import com.bima.expensetrackerapp.viewmodel.CategoryViewModel
import com.bima.expensetrackerapp.viewmodel.expense.AddExpenseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseForm(
    modifier: Modifier = Modifier,
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    addExpenseViewModel: AddExpenseViewModel = hiltViewModel(),
    navController: NavController
) {

    val categoryState by categoryViewModel.categoryExpenseState.collectAsState()
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.ROOT)
    val calendar = Calendar.getInstance()
    val expanded = remember { mutableStateOf(false) }
    var name by rememberSaveable {
        mutableStateOf("")
    }
    var description by rememberSaveable {
        mutableStateOf("")
    }
    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember {
        mutableStateOf(false)
    }
    var selectedDate by remember {
        mutableStateOf(calendar.timeInMillis)
    }
    var date by rememberSaveable {
        mutableStateOf("")
    }
    val selectedCategory = rememberSaveable {
        mutableStateOf("")
    }
    val category = rememberSaveable {
        mutableStateOf("")
    }
    val currency = rememberSaveable {
        mutableStateOf("")
    }

    val amount = rememberSaveable {
        mutableStateOf(0.0)
    }

    LaunchedEffect(expanded.value) {
        if (expanded.value) {
            categoryViewModel.getExpenseCategory()
        }
    }

    val composableScope = rememberCoroutineScope()

    val expense = Expense(
        name = name,
        description = description,
        categoryId = category.value,
        date = date,
        amount = amount.value
    )

    fun addExpense(expense: Expense) {
        addExpenseViewModel.createExpense(expense)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxSize().padding(32.dp)
    ) {
        OutlinedTextField(
            label = {
                Text(
                    text = "Name",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            value = name,
            onValueChange = { name = it },
            singleLine = true,
            isError = name.isEmpty(),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            label = {
                Text(
                    text = "Description",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            value = description,
            onValueChange = { description = it },
            singleLine = false,
            maxLines = 5,
            modifier = Modifier.height(100.dp).fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Dropdown(
                expanded = expanded,
                selectedCategory = selectedCategory,
                category = category,
                categoryState = categoryState,
            )
            IconButton(onClick = { showDatePicker = true }) {
                Icon(
                    modifier = modifier.size(50.dp),
                    tint = MaterialTheme.colorScheme.primary,
                    imageVector = Icons.Filled.DateRange, contentDescription = "select date")
            }
        }
        CurrencyTextField(text = currency, amount = amount)
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
            composableScope.launch {
                addExpense(expense)
                delay(1000L)
                navController.navigate(Graph.MAIN){
                    popUpTo(Graph.MAIN) {
                        inclusive = true
                    }
                }
            }
        }) {
            Text(text = "Add")
        }
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                        selectedDate = datePickerState.selectedDateMillis!!
                        date = formatter.format(Date(selectedDate))
                    }) {
                        Text(text = "Confirm")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                    }) {
                        Text(text = "Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                )
            }
        }
    }
}