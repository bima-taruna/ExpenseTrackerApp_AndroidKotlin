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
        return postgrest["category"].select(columns = Columns.ALL) {
            filter {
                eq("type", "expense")
            }
        }.decodeList()
    }

    override suspend fun getCategoryById(id: String): CategoryDto {
        return postgrest["category"].select(columns = Columns.ALL) {
            filter {
            eq("id", id)
            }
        }.decodeSingle()
    }

    override suspend fun getIncomeCategory(): List<CategoryDto> {
        return postgrest["category"].select(columns = Columns.ALL) {
            filter {
                eq("type", "income")
            }
        }.decodeList()
    }

    override suspend fun addCategory(name: String, type: String): Boolean {
        return try {
            val categoryDto = CategoryDto(
                name = name,
                type = type
            )
            postgrest["category"].insert(categoryDto)
            true
        } catch (e: java.lang.Exception) {
            throw e
        }
    }

    override suspend fun deleteCategory(id: String): Boolean {
        return try {
            postgrest["category"].delete {
                filter {
                    eq("id", id)
                }
            }
            true
        } catch (e: java.lang.Exception) {
            throw e
        }
    }

    override suspend fun updateCategory(id: String, name: String): Boolean {
        return try {
            postgrest["category"].update({
                set("name", name)
            }) {
                filter {
                    eq("id", id)
                }
            }
            true
        } catch (e: java.lang.Exception) {
            throw e
        }
    }
}