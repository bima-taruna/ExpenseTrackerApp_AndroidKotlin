package com.bima.expensetrackerapp.viewmodel.state.transaction

data class AddTransactionState(
    val isLoading:Boolean = false,
    val transaction: Boolean = false,
    val error:String = ""
)
