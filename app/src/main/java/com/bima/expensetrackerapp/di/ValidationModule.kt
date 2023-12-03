package com.bima.expensetrackerapp.di

import com.bima.expensetrackerapp.domain.use_case.validation.ValidateEmailUseCase
import com.bima.expensetrackerapp.domain.use_case.validation.ValidationUseCases
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
    fun provideValidationUseCases(): ValidationUseCases {
        return ValidationUseCases(
            validateEmail = ValidateEmailUseCase()
        )
    }
}