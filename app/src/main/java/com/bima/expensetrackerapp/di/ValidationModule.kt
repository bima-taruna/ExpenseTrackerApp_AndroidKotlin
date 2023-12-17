package com.bima.expensetrackerapp.di

import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateCategory
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateDate
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateEmail
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateLoginPassword
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateName
import com.bima.expensetrackerapp.domain.use_case.validation.ValidationLoginUseCases
import com.bima.expensetrackerapp.domain.use_case.validation.ValidationTransactionUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ValidationModule {
    @Singleton
    @Provides
    fun provideLoginValidationUseCases(): ValidationLoginUseCases {
        return ValidationLoginUseCases(
            validateEmail = ValidateEmail(),
            validatePasswordLogin = ValidateLoginPassword()
        )
    }

    @Singleton
    @Provides
    fun provideTransactionValidationUseCases(): ValidationTransactionUseCases {
        return ValidationTransactionUseCases(
            validateName = ValidateName(),
            validateDate = ValidateDate(),
            validateCategory = ValidateCategory()
        )
    }
}