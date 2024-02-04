package com.bima.expensetrackerapp.di

import com.bima.expensetrackerapp.data.repositoryImpl.TransactionRepositoryImpl
import com.bima.expensetrackerapp.domain.repository.TransactionRepository
import com.bima.expensetrackerapp.domain.use_case.expense.CreateExpenseUseCase
import com.bima.expensetrackerapp.domain.use_case.expense.DeleteExpenseUseCase
import com.bima.expensetrackerapp.domain.use_case.expense.ExpenseUseCases
import com.bima.expensetrackerapp.domain.use_case.expense.GetExpenseByIdUseCase
import com.bima.expensetrackerapp.domain.use_case.expense.GetExpensesUseCase
import com.bima.expensetrackerapp.domain.use_case.expense.UpdateExpenseUseCase
import com.bima.expensetrackerapp.domain.use_case.income.CreateIncomeUseCase
import com.bima.expensetrackerapp.domain.use_case.income.DeleteIncomeUseCase
import com.bima.expensetrackerapp.domain.use_case.income.GetIncomeByIdUseCase
import com.bima.expensetrackerapp.domain.use_case.income.GetIncomesUseCase
import com.bima.expensetrackerapp.domain.use_case.income.IncomeUseCases
import com.bima.expensetrackerapp.domain.use_case.income.UpdateIncomeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object TransactionModule {
    @Provides
    @Singleton
    fun provideTransactionRepository(postgrest: Postgrest): TransactionRepository {
        return TransactionRepositoryImpl(postgrest)
    }

    @Singleton
    @Provides
    fun provideExpenseUseCases(repository: TransactionRepository): ExpenseUseCases {
        return ExpenseUseCases(
            getExpenses = GetExpensesUseCase(repository),
            getExpenseById = GetExpenseByIdUseCase(repository),
            createExpense = CreateExpenseUseCase(repository),
            deleteExpense = DeleteExpenseUseCase(repository),
            updateExpense = UpdateExpenseUseCase(repository)
        )
    }

    @Singleton
    @Provides
    fun provideIncomeUseCases(repository: TransactionRepository): IncomeUseCases {
        return IncomeUseCases(
            getIncomes = GetIncomesUseCase(repository),
            getIncomeById = GetIncomeByIdUseCase(repository),
            createIncome = CreateIncomeUseCase(repository),
            deleteIncome = DeleteIncomeUseCase(repository),
            updateIncome = UpdateIncomeUseCase(repository)
        )
    }
}