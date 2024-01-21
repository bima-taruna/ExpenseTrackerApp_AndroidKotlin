package com.bima.expensetrackerapp.viewmodel.state.transaction

import com.bima.expensetrackerapp.domain.model.Transaction

data class TransactionState(
    val isLoading:Boolean = false,
    val transaction: Transaction? = null,
    val error:String = ""
)
