package com.bima.expensetrackerapp.viewmodel.state

import com.bima.expensetrackerapp.domain.model.Category
import com.bima.expensetrackerapp.domain.model.Expenses

data class CategoryState(
    val isLoading:Boolean = false,
    val category: List<Category>? = null,
    val error:String = ""
)
