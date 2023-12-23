package com.bima.expensetrackerapp.data.fakeRepository

import com.bima.expensetrackerapp.data.remote.ExpenseDto
import com.bima.expensetrackerapp.domain.model.Expense
import com.bima.expensetrackerapp.domain.repository.TransactionRepository

class FakeTransactionRepository:TransactionRepository {
    val expenseResult1 = ExpenseDto(
        id = "asdasdasdasd",
        name = "satu",
        description = null,
        categoryId = null,
        userId = null,
        amount = 23.000,
        date = "2023-12-01",
        createdAt = "1231231"
    )
    val expenseResult2 = ExpenseDto(
        id = "asdasdasd",
        name = "satu",
        description = null,
        categoryId = null,
        userId = null,
        amount = 23.000,
        date = "2023-12-01",
        createdAt = "1231231"
    )
    val expenseResult3 = ExpenseDto(
        id = "asdasdasd",
        name = "satu",
        description = null,
        categoryId = null,
        userId = null,
        amount = 23.000,
        date = "2023-12-01",
        createdAt = "1231231"
    )
    val expenseResult4 = ExpenseDto(
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


    override suspend fun getTransactions(type: String): List<ExpenseDto> {
        TODO("Not yet implemented")
    }

    override suspend fun createExpenses(expense: Expense): Boolean {
        TODO("Not yet implemented")
    }


}