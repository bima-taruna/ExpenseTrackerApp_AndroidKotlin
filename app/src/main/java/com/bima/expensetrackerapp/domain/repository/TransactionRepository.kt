package com.bima.expensetrackerapp.domain.repository

import com.bima.expensetrackerapp.data.remote.ExpenseDto
import com.bima.expensetrackerapp.domain.model.Expense

interface TransactionRepository {
    suspend fun getTransactions(type:String): List<ExpenseDto>
    suspend fun createExpenses(expense: Expense):Boolean
}