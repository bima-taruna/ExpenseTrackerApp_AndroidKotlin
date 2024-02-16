package com.bima.expensetrackerapp.common.form_event

sealed class BalanceFormEvent {
    data class AmountChanged(val amount:String):BalanceFormEvent()
    data object Submit : BalanceFormEvent()
}
