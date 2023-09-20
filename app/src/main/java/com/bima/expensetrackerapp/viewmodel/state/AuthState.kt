package com.bima.expensetrackerapp.viewmodel.state

data class AuthState(
    val isLoading:Boolean = false,
    val isSuccess:Boolean = false,
    val error:String = ""
)