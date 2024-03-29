package com.bima.expensetrackerapp.viewmodel.category

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bima.expensetrackerapp.ExpenseTrackerApp
import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.data.remote.toCategory
import com.bima.expensetrackerapp.domain.use_case.category.GetExpenseCategoryUseCase
import com.bima.expensetrackerapp.domain.use_case.category.GetIncomeCategoryUseCase
import com.bima.expensetrackerapp.viewmodel.state.category.CategoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val context: ExpenseTrackerApp,
    private val getExpenseCategoryUseCase: GetExpenseCategoryUseCase,
    private val getIncomeCategoryUseCase: GetIncomeCategoryUseCase,
) : ViewModel() {
    private val _categoryExpenseState = MutableStateFlow(CategoryState())
    val categoryExpenseState = _categoryExpenseState.asStateFlow()

    private val _categoryIncomeState = MutableStateFlow(CategoryState())
    val categoryIncomeState = _categoryIncomeState.asStateFlow()

    fun getExpenseCategory() {
        viewModelScope.launch {
            getExpenseCategoryUseCase.execute().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _categoryExpenseState.update {
                            it.copy(isLoading = false, category = result.data?.map { item ->
                                item.toCategory()
                            })
                        }
                    }

                    is Resource.Error -> {
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                        Log.d("error", result.message.toString())
                        _categoryExpenseState.update {
                            it.copy(isLoading = false, error = result.message.toString())
                        }
                    }

                    is Resource.Loading -> {
                        _categoryExpenseState.update {
                            it.copy(isLoading = true)
                        }
                    }
                }
            }.collect()
        }
    }

    fun getIncomeCategory() {
        viewModelScope.launch {
            getIncomeCategoryUseCase.execute().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _categoryIncomeState.update {
                            it.copy(isLoading = false, category = result.data?.map { item ->
                                item.toCategory()
                            })
                        }
                    }

                    is Resource.Error -> {
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                        Log.d("error", result.message.toString())
                        _categoryIncomeState.update {
                            it.copy(isLoading = false, error = result.message.toString())
                        }
                    }

                    is Resource.Loading -> {
                        _categoryIncomeState.update {
                            it.copy(isLoading = true)
                        }
                    }
                }
            }.collect()
        }
    }

}