package com.bima.expensetrackerapp.di

import com.bima.expensetrackerapp.data.repositoryImpl.CategoryRepositoryImpl
import com.bima.expensetrackerapp.domain.repository.CategoryRepository
import com.bima.expensetrackerapp.domain.use_case.category.AddExpenseCategoryUseCase
import com.bima.expensetrackerapp.domain.use_case.category.AddIncomeCategoryUseCase
import com.bima.expensetrackerapp.domain.use_case.category.CategoryUseCases
import com.bima.expensetrackerapp.domain.use_case.category.DeleteCategoryUseCase
import com.bima.expensetrackerapp.domain.use_case.category.GetCategoryByIdUseCase
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
            getCategoryByIdUseCase = GetCategoryByIdUseCase(repository),
            getIncomeCategory = GetIncomeCategoryUseCase(repository),
            addExpenseCategory = AddExpenseCategoryUseCase(repository),
            addIncomeCategory = AddIncomeCategoryUseCase(repository),
            deleteCategory =  DeleteCategoryUseCase(repository)
        )
    }
}