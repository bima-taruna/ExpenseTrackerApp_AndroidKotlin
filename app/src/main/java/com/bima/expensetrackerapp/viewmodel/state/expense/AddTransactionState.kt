package com.bima.expensetrackerapp.viewmodel.state.expense

data class AddTransactionState(
    val isLoading:Boolean = false,
    val transaction: Boolean = false,
    val error:String = ""
)
