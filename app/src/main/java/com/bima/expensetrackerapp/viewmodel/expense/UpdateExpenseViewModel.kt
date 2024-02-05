package com.bima.expensetrackerapp.viewmodel.expense

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bima.expensetrackerapp.ExpenseTrackerApp
import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.domain.model.Transaction
import com.bima.expensetrackerapp.domain.use_case.expense.UpdateExpenseUseCase
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateAmount
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateCategory
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateDate
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateName
import com.bima.expensetrackerapp.viewmodel.state.transaction.EventTransactionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
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
                            it.copy(isLoading = false)
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
}