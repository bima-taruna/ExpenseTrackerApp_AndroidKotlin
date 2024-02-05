package com.bima.expensetrackerapp.viewmodel.income

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bima.expensetrackerapp.ExpenseTrackerApp
import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.common.ValidationEvent
import com.bima.expensetrackerapp.common.form_event.TransactionFormEvent
import com.bima.expensetrackerapp.domain.model.Transaction
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateAmount
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateCategory
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateDate
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateName
import com.bima.expensetrackerapp.domain.use_case.income.CreateIncomeUseCase
import com.bima.expensetrackerapp.viewmodel.state.transaction.EventTransactionState
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
class AddIncomeViewModel @Inject constructor(
    private val context: ExpenseTrackerApp,
    private val createIncomeUseCase: CreateIncomeUseCase,
    private val validateName: ValidateName,
    private val validateDate: ValidateDate,
    private val validateCategory: ValidateCategory,
    private val validateAmount: ValidateAmount
) : ViewModel() {
    private val _addIncomeState = MutableStateFlow(EventTransactionState())
    val addIncomeState = _addIncomeState.asStateFlow()

    private val _incomeFormState = MutableStateFlow(TransactionFormState())
    val incomeFormState = _incomeFormState.asStateFlow()

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event:TransactionFormEvent) {
        when (event) {
            is TransactionFormEvent.NameChanged -> {
                _incomeFormState.value = _incomeFormState.value.copy(
                    name = event.name
                )
            }
            is TransactionFormEvent.AmountChanged -> {
                _incomeFormState.value = _incomeFormState.value.copy(
                    amount = event.amount
                )
            }
            is TransactionFormEvent.CategoryChanged -> {
                _incomeFormState.value = _incomeFormState.value.copy(
                    category = event.category
                )
            }
            is TransactionFormEvent.DateChanged -> {
                _incomeFormState.value = _incomeFormState.value.copy(
                    date = event.date
                )
            }
            is TransactionFormEvent.Submit -> {
                submitData()
            }
        }
    }


    fun createIncome(input:Transaction) {
        viewModelScope.launch {
            createIncomeUseCase.execute(input).onEach { result->
                when(result) {
                    is Resource.Success -> {
                        _addIncomeState.value = _addIncomeState.value.copy(
                            transaction = true,
                            isLoading = false
                        )
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Error -> {
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                        Log.d("error", result.message.toString())
                        _addIncomeState.value = _addIncomeState.value.copy(
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        _addIncomeState.value = _addIncomeState.value.copy(
                            isLoading = true
                        )
                    }
                }
            }.collect()
        }
    }

    private fun submitData() {
        val nameResult = validateName.execute(_incomeFormState.value.name)
        val dateResult = validateDate.execute(_incomeFormState.value.date)
        val categoryResult = validateCategory.execute(_incomeFormState.value.category)
        val amountResult = validateAmount.execute(_incomeFormState.value.amount)
        val hasError = listOf(
            nameResult,
            dateResult,
            categoryResult,
            amountResult
        ).any {
            !it.successful
        }
        if (hasError) {
            _incomeFormState.value = _incomeFormState.value.copy(
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