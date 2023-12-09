package com.bima.expensetrackerapp.common

sealed class LoginFormEvent {
    data class EmailChanged(val email:String) : LoginFormEvent()
    data class PasswordChanged(val password:String) : LoginFormEvent()
    data object Submit :LoginFormEvent()
}
