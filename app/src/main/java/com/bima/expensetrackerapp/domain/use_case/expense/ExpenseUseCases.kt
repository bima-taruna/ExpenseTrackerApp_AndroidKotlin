package com.bima.expensetrackerapp.domain.use_case.expense

data class ExpenseUseCases(
    val getExpenses: GetExpensesUseCase,
    val createExpense: CreateExpenseUseCase,
    val deleteExpense: DeleteExpenseUseCase
)
