package com.bima.expensetrackerapp.viewmodel.state.category

import com.bima.expensetrackerapp.domain.model.Category

data class CategoriesState(
    val isLoading:Boolean = false,
    val categories: List<Category>? = null,
    val error:String = ""
)
