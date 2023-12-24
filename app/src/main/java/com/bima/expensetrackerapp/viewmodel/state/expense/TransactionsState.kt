package com.bima.expensetrackerapp.viewmodel.state.expense

import com.bima.expensetrackerapp.domain.model.Transactions

data class TransactionsState(
    val isLoading:Boolean = false,
    val transactions: List<Transactions>? = null,
    val error:String = ""
)
