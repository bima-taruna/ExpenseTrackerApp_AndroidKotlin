package com.bima.expensetrackerapp.domain.use_case.form_validation

import com.bima.expensetrackerapp.R
import com.bima.expensetrackerapp.common.UiText
import com.bima.expensetrackerapp.common.ValidationResult
import com.bima.expensetrackerapp.common.isCurrency
import javax.inject.Inject

class ValidateAmount @Inject constructor() {
    fun execute(amount:String):ValidationResult {
        if (amount.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.amount_blank_message)
            )
        }
//        if (amount.isCurrency()) {
//            return ValidationResult(
//                successful = false,
//                errorMessage = UiText.StringResource(R.string.amount_not_valid)
//            )
//        }
        if (!isNumber(amount)) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.amount_not_valid)
            )
        }
        return ValidationResult(
            successful = true
        )
    }

    private fun isNumber(value: String): Boolean {
        return value.isEmpty() || Regex("^\\d+\$").matches(value)
    }
}