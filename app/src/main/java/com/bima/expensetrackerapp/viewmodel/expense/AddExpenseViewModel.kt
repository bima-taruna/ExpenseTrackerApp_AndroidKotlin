package com.bima.expensetrackerapp.viewmodel.expense

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bima.expensetrackerapp.ExpenseTrackerApp
import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.common.ValidationEvent
import com.bima.expensetrackerapp.common.form_event.TransactionFormEvent
import com.bima.expensetrackerapp.domain.model.Expense
import com.bima.expensetrackerapp.domain.use_case.expense.CreateExpenseUseCase
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateAmount
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateCategory
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateDate
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateName
import com.bima.expensetrackerapp.viewmodel.state.expense.AddExpenseState
import com.bima.expensetrackerapp.viewmodel.state.form.TransactionFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val context: ExpenseTrackerApp,
    private val createExpenseUseCase: CreateExpenseUseCase,
    private val validateName: ValidateName,
    private val validateDate: ValidateDate,
    private val validateCategory: ValidateCategory,
    private val validateAmount: ValidateAmount
) : ViewModel() {
    private val _addExpenseState = MutableStateFlow(AddExpenseState())
    val addExpenseState = _addExpenseState.asStateFlow()

    private val _transactionFormState = MutableStateFlow(TransactionFormState())
    val transactionFormState = _transactionFormState.asStateFlow()

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event:TransactionFormEvent) {
        when (event) {
            is TransactionFormEvent.NameChanged -> {
                _transactionFormState.value = _transactionFormState.value.copy(
                    name = event.name
                )
            }
            is TransactionFormEvent.AmountChanged -> {
                _transactionFormState.value = _transactionFormState.value.copy(
                    amount = event.amount
                )
            }
            is TransactionFormEvent.CategoryChanged -> {
                _transactionFormState.value = _transactionFormState.value.copy(
                    category = event.category
                )
            }
            is TransactionFormEvent.DateChanged -> {
                _transactionFormState.value = _transactionFormState.value.copy(
                    date = event.date
                )
            }
            is TransactionFormEvent.Submit -> {
                submitData()
            }
        }
    }


    fun createExpense(input:Expense) {
        viewModelScope.launch {
            createExpenseUseCase.execute(input).onEach {result->
                when(result) {
                    is Resource.Success -> {
                        _addExpenseState.value = _addExpenseState.value.copy(
                            expenses = true,
                            isLoading = false
                        )
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Error -> {
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                        Log.d("error", result.message.toString())
                        _addExpenseState.value = _addExpenseState.value.copy(
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        _addExpenseState.value = _addExpenseState.value.copy(
                            isLoading = true
                        )
                    }
                }
            }.collect()
        }
    }

    private fun submitData() {
        val nameResult = validateName.execute(_transactionFormState.value.name)
        val dateResult = validateDate.execute(_transactionFormState.value.date)
        val categoryResult = validateCategory.execute(_transactionFormState.value.category)
        val amountResult = validateAmount.execute(_transactionFormState.value.amount)
        val hasError = listOf(
            nameResult,
            dateResult,
            categoryResult,
            amountResult
        ).any {
            !it.successful
        }
        if (hasError) {
            _transactionFormState.value = _transactionFormState.value.copy(
                nameError = nameResult.errorMessage,
                dateError = dateResult.errorMessage,
                categoryError = categoryResult.errorMessage,
                amountError = amountResult.errorMessage
            )
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }
}