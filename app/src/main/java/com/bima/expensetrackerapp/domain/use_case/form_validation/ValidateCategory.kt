package com.bima.expensetrackerapp.domain.use_case.form_validation

import com.bima.expensetrackerapp.R
import com.bima.expensetrackerapp.common.UiText
import com.bima.expensetrackerapp.common.ValidationResult
import javax.inject.Inject

class ValidateCategory @Inject constructor() {
    fun execute(category:String): ValidationResult {
        if (category.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.category_blank_message)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}