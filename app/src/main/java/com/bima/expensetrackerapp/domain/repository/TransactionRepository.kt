package com.bima.expensetrackerapp.domain.repository

import com.bima.expensetrackerapp.data.remote.TransactionDto
import com.bima.expensetrackerapp.domain.model.Transaction

interface TransactionRepository {
    suspend fun getTransactions(type:String): List<TransactionDto>
    suspend fun getTransactionById(type: String, id: String):TransactionDto
    suspend fun createTransaction(transaction: Transaction, type:String):Boolean
    suspend fun deleteTransaction(id:String, type: String):Boolean
    suspend fun updateTransaction(
        id:String,
        name:String,
        description:String,
        categoryId:String,
        amount:Double,
        date:String,
        type: String
    ):Boolean
}