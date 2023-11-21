package com.bima.expensetrackerapp.data.fakeRepository

import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.data.remote.ExpenseDto
import com.bima.expensetrackerapp.domain.model.Expenses
import com.bima.expensetrackerapp.domain.repository.ExpensesRepository
import kotlinx.coroutines.flow.flow

class FakeExpensesRepository:ExpensesRepository {
    val expenseResult1 = ExpenseDto(
        id = 1,
        name = "satu",
        description = null,
        categoryId = null,
        userId = null,
        amount = 23.000,
        createdAt = "1231231"
    )
    val expenseResult2 = ExpenseDto(
        id = 1,
        name = "satu",
        description = null,
        categoryId = null,
        userId = null,
        amount = 23.000,
        createdAt = "1231231"
    )
    val expenseResult3 = ExpenseDto(
        id = 1,
        name = "satu",
        description = null,
        categoryId = null,
        userId = null,
        amount = 23.000,
        createdAt = "1231231"
    )
    val expenseResult4 = ExpenseDto(
        id = 1,
        name = "satu",
        description = null,
        categoryId = null,
        userId = null,
        amount = 23.000,
        createdAt = "1231231"
    )
    val listOfExpense = listOf(expenseResult1,expenseResult2,expenseResult3,expenseResult4)
    override suspend fun getExpenses(): List<ExpenseDto> {
        return listOfExpense
    }

    override suspend fun createExpenses(expense: Expenses): Boolean {
        return true
    }
}