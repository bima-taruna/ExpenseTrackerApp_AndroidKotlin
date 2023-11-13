package com.bima.expensetrackerapp.data.repositoryImpl

import com.bima.expensetrackerapp.data.remote.ExpenseDto
import com.bima.expensetrackerapp.domain.repository.ExpensesRepository
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject

class ExpensesRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : ExpensesRepository {
    override suspend fun getExpenses(): List<ExpenseDto> {
        return postgrest["expense"].select().decodeList()
    }
}