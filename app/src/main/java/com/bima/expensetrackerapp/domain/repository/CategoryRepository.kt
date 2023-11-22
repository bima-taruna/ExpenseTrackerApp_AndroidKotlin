package com.bima.expensetrackerapp.domain.repository

import com.bima.expensetrackerapp.data.remote.CategoryDto

interface CategoryRepository {
    suspend fun getExpenseCategory(): List<CategoryDto>

    suspend fun getExpenseCategoryById(id:String):CategoryDto

    suspend fun getIncomeCategory() : List<CategoryDto>
}