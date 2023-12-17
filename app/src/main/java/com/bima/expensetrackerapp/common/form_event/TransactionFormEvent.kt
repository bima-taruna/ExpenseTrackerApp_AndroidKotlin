package com.bima.expensetrackerapp.common.form_event

sealed class TransactionFormEvent {
    data class NameChanged(val name:String) : TransactionFormEvent()
//    data class DescriptionChanged(val desc:String) : TransactionFormEvent()
    data class DateChanged(val date:String) : TransactionFormEvent()
    data class CategoryChanged(val category:String) : TransactionFormEvent()
//    data class AmountChanged(val amount:Double):TransactionFormEvent()
    data object Submit : TransactionFormEvent()
}
