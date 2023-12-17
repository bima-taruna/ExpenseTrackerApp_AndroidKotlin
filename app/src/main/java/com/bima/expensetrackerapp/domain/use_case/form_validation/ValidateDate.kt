package com.bima.expensetrackerapp.domain.use_case.form_validation

import com.bima.expensetrackerapp.R
import com.bima.expensetrackerapp.common.UiText
import com.bima.expensetrackerapp.common.ValidationResult
import javax.inject.Inject

class ValidateDate @Inject constructor() {
    fun execute(date:String):ValidationResult {
        if (date.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.date_blank_message)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}