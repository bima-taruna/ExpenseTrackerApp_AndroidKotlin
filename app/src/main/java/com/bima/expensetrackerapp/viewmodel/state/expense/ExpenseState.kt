package com.bima.expensetrackerapp.viewmodel.state.expense

import com.bima.expensetrackerapp.domain.model.Balance
import com.bima.expensetrackerapp.domain.model.Expenses

data class ExpensesState(
    val isLoading:Boolean = false,
    val expenses: List<Expenses>? = null,
    val error:String = ""
)
