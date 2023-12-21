package com.bima.expensetrackerapp.viewmodel.state.expense

import com.bima.expensetrackerapp.domain.model.Expenses

data class AddExpenseState(
    val isLoading:Boolean = false,
    val expenses: Boolean = false,
    val error:String = ""
)
