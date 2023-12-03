package com.bima.expensetrackerapp.domain.use_case.validation

import com.bima.expensetrackerapp.R
import com.bima.expensetrackerapp.common.BaseUseCase
import com.bima.expensetrackerapp.common.UiText
import com.bima.expensetrackerapp.common.isEmailValid
import com.bima.expensetrackerapp.domain.model.ValidationResult

class ValidateEmailUseCase : BaseUseCase<String,ValidationResult> {
    override fun execute(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.email_blank_message)
            )
        }
        if (!input.isEmailValid()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.email_not_valid_message)
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}