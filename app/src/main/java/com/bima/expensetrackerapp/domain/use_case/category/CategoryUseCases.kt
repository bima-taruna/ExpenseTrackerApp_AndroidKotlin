package com.bima.expensetrackerapp.domain.use_case.category

data class CategoryUseCases(
    val getExpenseCategory : GetExpenseCategoryUseCase,
    val getExpenseCategoryByIdUseCase: GetExpenseCategoryByIdUseCase,
    val getIncomeCategory : GetIncomeCategoryUseCase,
    val addExpenseCategory : AddExpenseCategoryUseCase,
    val addIncomeCategory : AddIncomeCategoryUseCase
)
