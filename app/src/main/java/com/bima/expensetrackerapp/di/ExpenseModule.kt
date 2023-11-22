package com.bima.expensetrackerapp.di

import com.bima.expensetrackerapp.data.repositoryImpl.ExpensesRepositoryImpl
import com.bima.expensetrackerapp.domain.repository.ExpensesRepository
import com.bima.expensetrackerapp.domain.use_case.expense.CreateExpenseUseCase
import com.bima.expensetrackerapp.domain.use_case.expense.ExpenseUseCases
import com.bima.expensetrackerapp.domain.use_case.expense.GetExpensesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ExpenseModule {
    @Provides
    @Singleton
    fun provideExpensesRepository(postgrest: Postgrest): ExpensesRepository {
        return ExpensesRepositoryImpl(postgrest)
    }

    @Singleton
    @Provides
    fun provideExpenseUseCases(repository: ExpensesRepository): ExpenseUseCases {
        return ExpenseUseCases(
            getExpenses = GetExpensesUseCase(repository),
            createExpense = CreateExpenseUseCase(repository)
        )
    }
}