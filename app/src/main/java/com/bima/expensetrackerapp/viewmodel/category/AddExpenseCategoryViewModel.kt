package com.bima.expensetrackerapp.viewmodel.category

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bima.expensetrackerapp.ExpenseTrackerApp
import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.common.ValidationEvent
import com.bima.expensetrackerapp.common.form_event.CategoryFormEvent
import com.bima.expensetrackerapp.domain.use_case.category.AddExpenseCategoryUseCase
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateName
import com.bima.expensetrackerapp.viewmodel.state.category.EventCategoryState
import com.bima.expensetrackerapp.viewmodel.state.form.CategoryFormState
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
class AddExpenseCategoryViewModel @Inject constructor(
    private val context:ExpenseTrackerApp,
    private val addExpenseCategoryUseCase: AddExpenseCategoryUseCase,
    private val validateName: ValidateName
):ViewModel() {
    private val _addExpenseCategoryState = MutableStateFlow(EventCategoryState())
    val addExpenseCategoryState = _addExpenseCategoryState.asStateFlow()

    private val _expenseCategoryFormState = MutableStateFlow(CategoryFormState())
    val expenseCategoryFormState = _expenseCategoryFormState.asStateFlow()

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event:CategoryFormEvent) {
        when(event) {
            is CategoryFormEvent.NameChanged -> {
                _expenseCategoryFormState.update {
                    it.copy(name = event.name)
                }
            }
            is CategoryFormEvent.Submit -> {
                submitData()
            }
        }
    }

    fun addExpenseCategory(name:String) {
        viewModelScope.launch {
            addExpenseCategoryUseCase.execute(name).onEach {result->
                when(result) {
                    is Resource.Success -> {
                        _addExpenseCategoryState.update {
                            it.copy(isLoading = false, category = true)
                        }
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Error -> {
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                        Log.d("error", result.message.toString())
                        _addExpenseCategoryState.update {
                            it.copy(isLoading = false, error = result.message.toString())
                        }
                    }
                    is Resource.Loading -> {
                        _addExpenseCategoryState.update {
                            it.copy(isLoading = true)
                        }
                    }
                }
            }.collect()
        }
    }

    private fun submitData() {
        val nameResult = validateName.execute(_expenseCategoryFormState.value.name)
        if (!nameResult.successful) {
            _expenseCategoryFormState.update {
                it.copy(nameError = nameResult.errorMessage)
            }
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }
}