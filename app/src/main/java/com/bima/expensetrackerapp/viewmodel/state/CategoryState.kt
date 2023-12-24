package com.bima.expensetrackerapp.viewmodel.state

import com.bima.expensetrackerapp.domain.model.Category

data class CategoryState(
    val isLoading:Boolean = false,
    val category: List<Category>? = null,
    val error:String = ""
)
