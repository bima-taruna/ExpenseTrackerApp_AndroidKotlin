package com.bima.expensetrackerapp.data.repositoryImpl

import com.bima.expensetrackerapp.data.remote.CategoryDto
import com.bima.expensetrackerapp.domain.repository.CategoryRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
   private val postgrest: Postgrest
):CategoryRepository {
    override suspend fun getExpenseCategory(): List<CategoryDto> {
        return postgrest["category"].select(columns = Columns.list("id","name","type","created_by")) {
            eq("type", "expense")
        }.decodeList()
    }

    override suspend fun getExpenseCategoryById(id: String): CategoryDto {
        return postgrest["category"].select(columns = Columns.list("id")) {
            eq("id", id)
        }.decodeSingle()
    }

    override suspend fun getIncomeCategory(): List<CategoryDto> {
        return postgrest["category"].select(columns = Columns.list("type")) {
            eq("type", "income")
        }.decodeList()
    }
}