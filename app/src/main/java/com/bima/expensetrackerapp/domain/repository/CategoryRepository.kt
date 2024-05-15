package com.bima.expensetrackerapp.domain.repository

import com.bima.expensetrackerapp.data.remote.CategoryDto
import com.bima.expensetrackerapp.domain.model.Category

interface CategoryRepository {
    suspend fun getExpenseCategory(): List<CategoryDto>

    suspend fun getCategoryById(id:String):CategoryDto

    suspend fun getIncomeCategory() : List<CategoryDto>

    suspend fun addCategory(name:String,type:String) : Boolean

    suspend fun deleteCategory(id:String) : Boolean

    suspend fun updateCategory(id: String, name:String):Boolean
}