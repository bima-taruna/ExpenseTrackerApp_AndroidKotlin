package com.bima.expensetrackerapp.domain.use_case.form_validation

import com.bima.expensetrackerapp.R
import com.bima.expensetrackerapp.common.UiText
import com.bima.expensetrackerapp.common.ValidationResult
import com.bima.expensetrackerapp.common.isAlphabet
import javax.inject.Inject

class ValidateName @Inject constructor() {
    fun execute(name:String):ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.name_blank_message)
            )
        }
        if (name.length < 2) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.name_too_short_message)
            )
        }
        if (!name.isAlphabet()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.name_not_valid)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}