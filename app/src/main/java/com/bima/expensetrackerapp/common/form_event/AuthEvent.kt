package com.bima.expensetrackerapp.common.form_event

sealed class AuthEvent {
    data class EmailChanged(val email:String) : AuthEvent()
    data class PasswordChanged(val password:String) : AuthEvent()
    data object Login:AuthEvent()
    data object Logout:AuthEvent()
    data object Submit : AuthEvent()
}
