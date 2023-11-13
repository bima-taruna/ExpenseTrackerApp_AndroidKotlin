package com.bima.expensetrackerapp.domain.repository

import com.bima.expensetrackerapp.data.remote.ExpenseDto

interface ExpensesRepository {
    suspend fun getExpenses(): List<ExpenseDto>
}