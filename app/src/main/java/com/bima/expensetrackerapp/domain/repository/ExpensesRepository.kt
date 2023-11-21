package com.bima.expensetrackerapp.domain.repository

import com.bima.expensetrackerapp.data.remote.ExpenseDto
import com.bima.expensetrackerapp.domain.model.Expense

interface ExpensesRepository {
    suspend fun getExpenses(): List<ExpenseDto>
    suspend fun createExpenses(expense: Expense):Boolean
}