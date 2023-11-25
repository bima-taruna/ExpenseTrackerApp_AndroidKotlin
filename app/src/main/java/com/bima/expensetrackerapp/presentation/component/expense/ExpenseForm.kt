package com.bima.expensetrackerapp.presentation.component.expense

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bima.expensetrackerapp.domain.model.Expense
import com.bima.expensetrackerapp.presentation.component.CurrencyTextField
import com.bima.expensetrackerapp.presentation.navigation.Graph
import com.bima.expensetrackerapp.presentation.navigation.Screen
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
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
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
    var selectedCategory by rememberSaveable {
        mutableStateOf("")
    }
    var category by rememberSaveable {
        mutableStateOf("")
    }
    val currency = rememberSaveable {
        mutableStateOf("")
    }

    val amount = rememberSaveable {
        mutableStateOf(0.0)
    }

    LaunchedEffect(expanded) {
        if (expanded) {
            categoryViewModel.getExpenseCategory()
        }
    }

    val composableScope = rememberCoroutineScope()



    val expense = Expense(
        name = name,
        description = description,
        categoryId = category,
        date = date,
        amount = amount.value
    )

    fun addExpense(expense: Expense) {
        addExpenseViewModel.createExpense(expense)
    }

    Log.d("name", name)
    Log.d("description", description)
    Log.d("date", date)
    Log.d("category", category)
    Log.d("amount", amount.toString())


    Column {
        OutlinedTextField(value = name, onValueChange = { name = it })
        OutlinedTextField(value = description, onValueChange = { description = it })
        Row {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }) {
                TextField(
                    value = selectedCategory,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    if (categoryState.isLoading) {
                        Box(
                            modifier = modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        categoryState.category?.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item.name.toString()) },
                                onClick = {
                                    selectedCategory = item.name.toString()
                                    category = item.id.toString()
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
            IconButton(onClick = { showDatePicker = true }) {
                Icon(imageVector = Icons.Filled.DateRange, contentDescription = "select date")
            }
        }
        CurrencyTextField(text = currency, amount = amount)
        Button(onClick = {
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