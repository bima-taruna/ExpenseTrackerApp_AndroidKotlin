package com.bima.expensetrackerapp.viewmodel.state.form

import com.bima.expensetrackerapp.common.UiText

data class BalanceFormState(
    val amount: String = "",
    val amountError: UiText? = null
)
