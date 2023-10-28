package com.bima.expensetrackerapp.viewmodel.state

import com.bima.expensetrackerapp.domain.model.Balance

data class BalanceState(
    val isLoading:Boolean = false,
    val balance: Balance? = null,
    val error:String = ""
)
