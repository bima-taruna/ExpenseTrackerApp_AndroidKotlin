package com.bima.expensetrackerapp.common

data class ValidationResult(
    val successful:Boolean,
    val errorMessage:UiText? = null
)
