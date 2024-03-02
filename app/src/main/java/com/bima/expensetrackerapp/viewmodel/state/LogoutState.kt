package com.bima.expensetrackerapp.viewmodel.state

data class LogoutState(
    val isLoading:Boolean = false,
    val isLogout:Boolean = false,
    val error:String = ""
)
