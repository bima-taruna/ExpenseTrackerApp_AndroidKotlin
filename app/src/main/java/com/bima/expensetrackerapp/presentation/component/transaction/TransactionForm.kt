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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.bima.expensetrackerapp.domain.model.Transaction
import com.bima.expensetrackerapp.presentation.component.form.CurrencyTextField
import com.bima.expensetrackerapp.presentation.component.form.DatePickerCustom
import com.bima.expensetrackerapp.presentation.component.form.Dropdown
import com.bima.expensetrackerapp.presentation.component.form.TextArea
import com.bima.expensetrackerapp.presentation.component.form.TextField
import com.bima.expensetrackerapp.presentation.navigation.Graph
import com.bima.expensetrackerapp.viewmodel.state.CategoryState
import com.bima.expensetrackerapp.viewmodel.state.form.TransactionFormState
import com.bima.expensetrackerapp.viewmodel.state.transaction.EventTransactionState
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
    categoryState: CategoryState,
    formState: TransactionFormState,
    transactionEventState: EventTransactionState,
    state: Transaction? = null,
    id:String = "",
    isUpdate: Boolean = false,
    getCategory: () -> Unit,
    createExpense: (transaction: Transaction) -> Unit = {},
    updateExpense: (id: String, transaction: Transaction) -> Unit = { _: String, _: Transaction -> },
    validationEvent: Flow<ValidationEvent>,
    onEvent: (event: TransactionFormEvent) -> Unit,
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

    val composableScope = rememberCoroutineScope()

    val addTransaction = { transaction: Transaction ->
        createExpense(transaction)
    }

    val updateTransaction = { s: String, transaction: Transaction ->
        updateExpense(s, transaction)
    }

    LaunchedEffect(state) {
        name = state?.name  ?: ""
        description.value = state?.description ?: ""
        date = state?.date ?: ""
        category.value = state?.categoryId ?: ""
        amount.value = state?.amount ?: 0.0
        currency.value = if (state?.amount != null) state.amount.toInt().toString() else ""
        onEvent(TransactionFormEvent.NameChanged(state?.name ?: ""))
        onEvent(TransactionFormEvent.DateChanged(state?.date ?: ""))
        onEvent(TransactionFormEvent.CategoryChanged(state?.categoryId ?: ""))
        onEvent(TransactionFormEvent.AmountChanged(state?.amount?.toInt().toString()))
    }

    LaunchedEffect(expanded.value) {
        if (expanded.value) {
            getCategory()
        }
    }

    LaunchedEffect(transactionEventState) {
        validationEvent.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    if (isUpdate) {
                        updateTransaction(id, Transaction(
                            name = name,
                            description = description.value,
                            categoryId = category.value,
                            date = date,
                            amount = amount.value
                        ))
                    } else {
                        addTransaction(
                            Transaction(
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
    }

    LaunchedEffect(transactionEventState.transaction) {
        if (transactionEventState.transaction) {
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
        TextField(
            label = "Name",
            onValueChange = {
                onEvent(TransactionFormEvent.NameChanged(it))
                name = it
            },
            isError = formState.nameError != null,
            value = name
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
            Text(text = if (isUpdate) "Update" else "Add")
        }
        if (showDatePicker) {
            DatePickerCustom(
                confirmDate = {
                    showDatePicker = false
                    selectedDate =
                        (datePickerState.selectedDateMillis ?: System.currentTimeMillis())
                    date = formatter.format(
                        Date(selectedDate)
                    )
                    onEvent(
                        TransactionFormEvent.DateChanged(
                            date
                        )
                    )
                },
                dismissRequest = {
                    showDatePicker = false
                },
                dismiss = {
                    showDatePicker = false
                },
                state = datePickerState
            )
        }
    }
}