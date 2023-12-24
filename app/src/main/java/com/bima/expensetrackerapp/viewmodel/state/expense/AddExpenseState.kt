package com.bima.expensetrackerapp.viewmodel.state.expense

data class AddExpenseState(
    val isLoading:Boolean = false,
    val expenses: Boolean = false,
    val error:String = ""
)
