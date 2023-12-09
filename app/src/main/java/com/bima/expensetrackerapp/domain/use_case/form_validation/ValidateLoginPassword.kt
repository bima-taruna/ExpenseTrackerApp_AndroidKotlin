package com.bima.expensetrackerapp.domain.use_case.form_validation

import com.bima.expensetrackerapp.R
import com.bima.expensetrackerapp.common.UiText
import com.bima.expensetrackerapp.common.ValidationResult
import javax.inject.Inject

class ValidateLoginPassword @Inject constructor() {
    fun execute(password:String):ValidationResult {
        if(password.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.password_blank_message)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}