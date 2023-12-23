package com.bima.expensetrackerapp.data.repositoryImpl

import com.bima.expensetrackerapp.data.remote.ExpenseDto
import com.bima.expensetrackerapp.domain.model.Expense
import com.bima.expensetrackerapp.domain.repository.ExpensesRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

class ExpensesRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : ExpensesRepository {
    override suspend fun getTransactions(type:String): List<ExpenseDto> {
        return postgrest[type].select(Columns.ALL).decodeList()
    }

    override suspend fun createExpenses(expense: Expense): Boolean {
        return try {
            val expenseDto = ExpenseDto(
                name = expense.name,
                description = expense.description,
                amount = expense.amount,
                date = expense.date,
                categoryId = expense.categoryId,
            )
            postgrest["expense"].insert(expenseDto)
            true
        } catch (e:java.lang.Exception) {
            throw e
        }

    }
}