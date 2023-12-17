package com.bima.expensetrackerapp.viewmodel.state.form

import com.bima.expensetrackerapp.common.UiText

data class TransactionFormState(
    val name:String = "",
    val nameError:UiText? = null,
    val date:String = "",
    val dateError:UiText? = null,
    val category: String = "",
    val categoryError:UiText? = null,
    val amount: String = "",
    val amountError:UiText? = null
)
