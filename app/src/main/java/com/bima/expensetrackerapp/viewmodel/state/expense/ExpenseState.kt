package com.bima.expensetrackerapp.viewmodel.state.expense

import com.bima.expensetrackerapp.domain.model.Transactions

data class ExpensesState(
    val isLoading:Boolean = false,
    val expens: List<Transactions>? = null,
    val error:String = ""
)
