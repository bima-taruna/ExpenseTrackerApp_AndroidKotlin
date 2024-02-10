package com.bima.expensetrackerapp.viewmodel.expense

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bima.expensetrackerapp.ExpenseTrackerApp
import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.common.ValidationEvent
import com.bima.expensetrackerapp.common.form_event.TransactionFormEvent
import com.bima.expensetrackerapp.domain.model.Transaction
import com.bima.expensetrackerapp.domain.use_case.expense.UpdateExpenseUseCase
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateAmount
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateCategory
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateDate
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateName
import com.bima.expensetrackerapp.viewmodel.state.form.TransactionFormState
import com.bima.expensetrackerapp.viewmodel.state.transaction.EventTransactionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateExpenseViewModel @Inject constructor(
    private val context: ExpenseTrackerApp,
    private val updateExpenseUseCase: UpdateExpenseUseCase,
    private val validateName: ValidateName,
    private val validateDate: ValidateDate,
    private val validateCategory: ValidateCategory,
    private val validateAmount: ValidateAmount
): ViewModel() {
    private val _updateExpenseState = MutableStateFlow(EventTransactionState())
    val updateExpenseState = _updateExpenseState.asStateFlow()

    private val _updateExpenseFormState = MutableStateFlow(TransactionFormState())
    val updateExpenseFormState = _updateExpenseFormState.asStateFlow()

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event:TransactionFormEvent) {
        when(event) {
            is TransactionFormEvent.NameChanged -> {
                _updateExpenseFormState.update {
                    it.copy(name = event.name)
                }
            }
            is TransactionFormEvent.AmountChanged -> {
                _updateExpenseFormState.update {
                    it.copy(amount = event.amount)
                }
            }
            is TransactionFormEvent.CategoryChanged -> {
                _updateExpenseFormState.update {
                    it.copy(category = event.category)
                }
            }
            is TransactionFormEvent.DateChanged -> {
                _updateExpenseFormState.update {
                    it.copy(date = event.date)
                }
            }
            is TransactionFormEvent.Submit -> {
                submitData()
            }
        }
    }

    fun updateExpense(id:String, input:Transaction) {
        viewModelScope.launch {
            updateExpenseUseCase.execute(id,input).onEach { result ->
                when(result) {
                    is Resource.Success -> {
                        _updateExpenseState.update {
                            it.copy(transaction = true, isLoading = false)
                        }
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Error -> {
                        _updateExpenseState.update {
                            it.copy(isLoading = false,
                                error = result.message.toString()
                            )
                        }
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        _updateExpenseState.update {
                            it.copy(isLoading = true)
                        }
                    }
                }
            }.collect()
        }
    }

    private fun submitData() {
        val nameResult = validateName.execute(_updateExpenseFormState.value.name)
        val dateResult = validateDate.execute(_updateExpenseFormState.value.date)
        val categoryResult = validateCategory.execute(_updateExpenseFormState.value.category)
        val amountResult = validateAmount.execute(_updateExpenseFormState.value.amount)
        val hasError = listOf(
            nameResult,
            dateResult,
            categoryResult,
            amountResult
        ).any {
            !it.successful
        }
        if (hasError) {
            _updateExpenseFormState.value = _updateExpenseFormState.value.copy(
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
