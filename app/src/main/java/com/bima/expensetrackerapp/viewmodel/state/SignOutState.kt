package com.bima.expensetrackerapp.viewmodel.state

data class SignOutState(
    val isLoading:Boolean = false,
    val isSuccess:Boolean = false,
    val error:String = ""
)