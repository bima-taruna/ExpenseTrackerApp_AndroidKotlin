package com.bima.expensetrackerapp.common.form_event

sealed class LoginFormEvent {
    data class EmailChanged(val email:String) : LoginFormEvent()
    data class PasswordChanged(val password:String) : LoginFormEvent()
    data object Submit : LoginFormEvent()
}
