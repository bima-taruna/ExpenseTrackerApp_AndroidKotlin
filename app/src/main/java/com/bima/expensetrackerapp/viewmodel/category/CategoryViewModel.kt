package com.bima.expensetrackerapp.viewmodel.category

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bima.expensetrackerapp.ExpenseTrackerApp
import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.data.remote.toCategory
import com.bima.expensetrackerapp.domain.use_case.category.DeleteCategoryUseCase
import com.bima.expensetrackerapp.domain.use_case.category.GetCategoryByIdUseCase
import com.bima.expensetrackerapp.domain.use_case.category.GetExpenseCategoryUseCase
import com.bima.expensetrackerapp.domain.use_case.category.GetIncomeCategoryUseCase
import com.bima.expensetrackerapp.viewmodel.state.category.CategoriesState
import com.bima.expensetrackerapp.viewmodel.state.category.CategoryState
import com.bima.expensetrackerapp.viewmodel.state.category.EventCategoryState
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
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase,
    private val getIncomeCategoryUseCase: GetIncomeCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
) : ViewModel() {
    private val _categoriesExpenseState = MutableStateFlow(CategoriesState())
    val categoriesExpenseState = _categoriesExpenseState.asStateFlow()

    private val _categoriesIncomeState = MutableStateFlow(CategoriesState())
    val categoriesIncomeState = _categoriesIncomeState.asStateFlow()

    private val _deleteCategoryState = MutableStateFlow(EventCategoryState())
    val deleteCategoryState = _deleteCategoryState.asStateFlow()

    private val _categoryState = MutableStateFlow(CategoryState())
    val categoryState = _categoryState.asStateFlow()

    fun getExpenseCategory() {
        viewModelScope.launch {
            getExpenseCategoryUseCase.execute().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _categoriesExpenseState.update {
                            it.copy(isLoading = false, categories = result.data?.map { item ->
                                item.toCategory()
                            })
                        }
                    }

                    is Resource.Error -> {
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                        Log.d("error", result.message.toString())
                        _categoriesExpenseState.update {
                            it.copy(isLoading = false, error = result.message.toString())
                        }
                    }

                    is Resource.Loading -> {
                        _categoriesExpenseState.update {
                            it.copy(isLoading = true)
                        }
                    }
                }
            }.collect()
        }
    }

    fun getCategoryById(id: String) {
        viewModelScope.launch {
            getCategoryByIdUseCase.execute(id).onEach { result->
                when (result) {
                    is Resource.Success -> {
                        _categoryState.update {
                            it.copy(isLoading = false, category = result.data)
                        }
                    }
                    is Resource.Error -> {
                        _categoryState.update {
                            it.copy(isLoading = false, error = result.message.toString())
                        }
                    }
                    is Resource.Loading -> {
                        _categoryState.update {
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
                        _categoriesIncomeState.update {
                            it.copy(isLoading = false, categories = result.data?.map { item ->
                                item.toCategory()
                            })
                        }
                    }

                    is Resource.Error -> {
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                        Log.d("error", result.message.toString())
                        _categoriesIncomeState.update {
                            it.copy(isLoading = false, error = result.message.toString())
                        }
                    }

                    is Resource.Loading -> {
                        _categoriesIncomeState.update {
                            it.copy(isLoading = true)
                        }
                    }
                }
            }.collect()
        }
    }

    fun deleteCategory(id:String) {
        viewModelScope.launch {
            deleteCategoryUseCase.execute(id).onEach { result->
               when(result) {
                   is Resource.Success -> {
                       _deleteCategoryState.update {
                           it.copy(isLoading = false, category = result.data ?: false)
                       }
                       Toast.makeText(context, "category deleted", Toast.LENGTH_SHORT).show()
                   }
                   is Resource.Error -> {
                       Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                       Log.d("error", result.message.toString())
                       _deleteCategoryState.update {
                           it.copy(isLoading = false, error = result.message.toString())
                       }
                   }
                   is Resource.Loading -> {
                       _deleteCategoryState.update {
                           it.copy(isLoading = true)
                       }
                   }
               }
            }.collect()
        }
    }

}