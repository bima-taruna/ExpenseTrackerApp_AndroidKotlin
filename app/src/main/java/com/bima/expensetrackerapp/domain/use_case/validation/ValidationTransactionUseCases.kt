package com.bima.expensetrackerapp.domain.use_case.validation

import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateAmount
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateCategory
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateDate
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateName

data class ValidationTransactionUseCases(
    val validateName : ValidateName,
    val validateDate: ValidateDate,
    val validateCategory: ValidateCategory,
    val validateAmount:ValidateAmount
)
