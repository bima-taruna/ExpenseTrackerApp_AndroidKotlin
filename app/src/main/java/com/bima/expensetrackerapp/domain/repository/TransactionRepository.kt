package com.bima.expensetrackerapp.domain.repository

import com.bima.expensetrackerapp.data.remote.TransactionDto
import com.bima.expensetrackerapp.domain.model.Expense

interface TransactionRepository {
    suspend fun getTransactions(type:String): List<TransactionDto>
    suspend fun createTransaction(transaction: Expense, type:String):Boolean
}