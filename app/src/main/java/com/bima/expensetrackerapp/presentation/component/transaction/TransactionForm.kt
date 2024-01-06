package com.bima.expensetrackerapp.presentation.component.transaction

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.navigation.NavController
import com.bima.expensetrackerapp.common.ValidationEvent
import com.bima.expensetrackerapp.common.form_event.TransactionFormEvent
import com.bima.expensetrackerapp.domain.model.Expense
import com.bima.expensetrackerapp.presentation.component.form.CurrencyTextField
import com.bima.expensetrackerapp.presentation.component.form.Dropdown
import com.bima.expensetrackerapp.presentation.component.form.TextArea
import com.bima.expensetrackerapp.presentation.navigation.Graph
import com.bima.expensetrackerapp.viewmodel.state.CategoryState
import com.bima.expensetrackerapp.viewmodel.state.expense.AddTransactionState
import com.bima.expensetrackerapp.viewmodel.state.form.TransactionFormState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionForm(
    modifier: Modifier = Modifier,
    categoryState:CategoryState,
    formState:TransactionFormState,
    addExpenseState:AddTransactionState,
    getCategory:() -> Unit,
    createExpense:(expense:Expense) -> Unit,
    validationEvent: Flow<ValidationEvent>,
    onEvent:(event:TransactionFormEvent)->Unit,
    navController: NavController,
) {

    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.ROOT)
    val calendar = Calendar.getInstance()
    val expanded = remember { mutableStateOf(false) }
    val context = LocalContext.current

    val description = rememberSaveable {
        mutableStateOf("")
    }
    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember {
        mutableStateOf(false)
    }
    var selectedDate by remember {
        mutableStateOf(calendar.timeInMillis)
    }

    var date by remember {
        mutableStateOf("")
    }

    var name by remember {
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
            getCategory()
        }
    }

    val composableScope = rememberCoroutineScope()


    fun addExpense(expense: Expense) {
       createExpense(expense)
    }

    LaunchedEffect(context, addExpenseState) {
        validationEvent.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    addExpense(
                        Expense(
                            name = name,
                            description = description.value,
                            categoryId = category.value,
                            date = date,
                            amount = amount.value
                        )
                    )
                }
            }
        }
    }



    LaunchedEffect(addExpenseState.transaction) {
        if (addExpenseState.transaction) {
            navController.navigate(Graph.MAIN) {
                popUpTo(Graph.MAIN) {
                    inclusive = true
                }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp)
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
            onValueChange = {
                onEvent(TransactionFormEvent.NameChanged(it))
                name = it
            },
            singleLine = true,
            isError = formState.nameError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (formState.nameError != null) {
            Text(
                text = formState.nameError.asString(context),
                color = MaterialTheme.colorScheme.error
            )
        }
        TextArea(description = description)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Dropdown(
                expanded = expanded,
                selectedCategory = selectedCategory,
                category = category,
                categoryState = categoryState,
                changeValue = {
                    onEvent(TransactionFormEvent.CategoryChanged(category.value))
                },
                isError = formState.categoryError != null,
            )
            IconButton(onClick = { showDatePicker = true }) {
                Icon(
                    modifier = modifier.size(50.dp),
                    tint = MaterialTheme.colorScheme.primary,
                    imageVector = Icons.Filled.DateRange, contentDescription = "select date"
                )
            }
        }
        if (formState.categoryError != null) {
            Text(
                text = formState.categoryError.asString(context),
                color = MaterialTheme.colorScheme.error
            )
        }
        if (formState.dateError != null) {
            Text(
                text = formState.dateError.asString(context),
                color = MaterialTheme.colorScheme.error
            )
        }
        CurrencyTextField(
            text = currency,
            isError = formState.amountError != null,
            amount = amount,
            onValueChange = {
                onEvent(TransactionFormEvent.AmountChanged(currency.value))
            })
        if (formState.amountError != null) {
            Text(
                text = formState.amountError.asString(context),
                color = MaterialTheme.colorScheme.error
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                composableScope.launch {
                    onEvent(TransactionFormEvent.Submit)
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
                        selectedDate = (datePickerState.selectedDateMillis ?: formatter.format(Date())) as Long
                        date =  formatter.format(
                            Date(selectedDate)
                        )
                        onEvent(
                            TransactionFormEvent.DateChanged(
                                formatter.format(
                                    Date(selectedDate)
                                )
                            )
                        )
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