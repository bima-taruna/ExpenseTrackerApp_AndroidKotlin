package com.bima.expensetrackerapp.viewmodel.state.transaction

data class EventTransactionState(
    val isLoading:Boolean = false,
    val transaction: Boolean = false,
    val error:String = ""
)
