package com.bima.expensetrackerapp.data.fakeRepository

import com.bima.expensetrackerapp.data.remote.TransactionDto
import com.bima.expensetrackerapp.domain.model.Transaction
import com.bima.expensetrackerapp.domain.repository.TransactionRepository

class FakeTransactionRepository:TransactionRepository {
    val expenseResult1 = TransactionDto(
        id = "asdasdasdasd",
        name = "satu",
        description = null,
        categoryId = null,
        userId = null,
        amount = 23.000,
        date = "2023-12-01",
        createdAt = "1231231"
    )
    val expenseResult2 = TransactionDto(
        id = "asdasdasd",
        name = "satu",
        description = null,
        categoryId = null,
        userId = null,
        amount = 23.000,
        date = "2023-12-01",
        createdAt = "1231231"
    )
    val expenseResult3 = TransactionDto(
        id = "asdasdasd",
        name = "satu",
        description = null,
        categoryId = null,
        userId = null,
        amount = 23.000,
        date = "2023-12-01",
        createdAt = "1231231"
    )
    val expenseResult4 = TransactionDto(
        id = "dasdasdas",
        name = "satu",
        description = null,
        categoryId = null,
        userId = null,
        amount = 23.000,
        date = "2023-12-01",
        createdAt = "1231231"
    )
    val listOfExpense = listOf(expenseResult1,expenseResult2,expenseResult3,expenseResult4)


    override suspend fun getTransactions(type: String): List<TransactionDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getTransactionById(type: String, id: String): TransactionDto {
        TODO("Not yet implemented")
    }

    override suspend fun createTransaction(transaction: Transaction, type: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTransaction(id: String, type: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateTransaction(id: String, data: Transaction, type: String): Boolean {
        TODO("Not yet implemented")
    }

//    override suspend fun updateTransaction(
//        id: String,
//        name: String,
//        description: String,
//        categoryId: String,
//        amount: Double,
//        date: String,
//        type: String,
//    ): Boolean {
//        TODO("Not yet implemented")
//    }
}