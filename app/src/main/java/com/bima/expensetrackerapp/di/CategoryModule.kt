package com.bima.expensetrackerapp.di

import com.bima.expensetrackerapp.data.repositoryImpl.CategoryRepositoryImpl
import com.bima.expensetrackerapp.data.repositoryImpl.ExpensesRepositoryImpl
import com.bima.expensetrackerapp.domain.repository.BalanceRepository
import com.bima.expensetrackerapp.domain.repository.CategoryRepository
import com.bima.expensetrackerapp.domain.repository.ExpensesRepository
import com.bima.expensetrackerapp.domain.use_case.balance.BalanceUseCases
import com.bima.expensetrackerapp.domain.use_case.balance.GetBalanceUseCase
import com.bima.expensetrackerapp.domain.use_case.category.CategoryUseCases
import com.bima.expensetrackerapp.domain.use_case.category.GetExpenseCategoryUseCase
import com.bima.expensetrackerapp.domain.use_case.category.GetIncomeCategoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CategoryModule {

    @Provides
    @Singleton
    fun provideCategoryRepository(postgrest: Postgrest): CategoryRepository {
        return CategoryRepositoryImpl(postgrest)
    }

    @Singleton
    @Provides
    fun provideCategoryUseCases(repository: CategoryRepository): CategoryUseCases {
        return CategoryUseCases(
            getExpenseCategory = GetExpenseCategoryUseCase(repository),
            getIncomeCategory = GetIncomeCategoryUseCase(repository)
        )
    }
}