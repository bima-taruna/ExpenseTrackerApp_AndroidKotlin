package com.bima.expensetrackerapp.domain.model

import com.bima.expensetrackerapp.common.UiText

data class ValidationResult(
    val successful:Boolean,
    val errorMessage: UiText? = null
)
