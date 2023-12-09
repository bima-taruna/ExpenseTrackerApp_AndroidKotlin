package com.bima.expensetrackerapp.domain.use_case.validation

import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateEmail
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateLoginPassword

data class ValidationLoginUseCases(
    val validateEmail:ValidateEmail,
    val validatePasswordLogin:ValidateLoginPassword
)
