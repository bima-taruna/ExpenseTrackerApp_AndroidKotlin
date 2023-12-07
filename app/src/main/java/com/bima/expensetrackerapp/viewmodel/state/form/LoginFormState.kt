package com.bima.expensetrackerapp.viewmodel.state.form

import com.bima.expensetrackerapp.common.UiText

data class LoginFormState(
    val email:String = "",
    val emailError: UiText? = null
)
