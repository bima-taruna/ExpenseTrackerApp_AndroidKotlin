package com.bima.expensetrackerapp.domain.use_case.form_validation

import android.util.Patterns
import com.bima.expensetrackerapp.R
import com.bima.expensetrackerapp.common.UiText
import com.bima.expensetrackerapp.common.ValidationResult

class ValidateEmail {
    fun execute(email:String):ValidationResult{
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.email_blank_message)
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.email_not_valid)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}