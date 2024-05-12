package com.bima.expensetrackerapp.domain.use_case.category

data class CategoryUseCases(
    val getExpenseCategory : GetExpenseCategoryUseCase,
    val getCategoryByIdUseCase: GetCategoryByIdUseCase,
    val getIncomeCategory : GetIncomeCategoryUseCase,
    val addExpenseCategory : AddExpenseCategoryUseCase,
    val addIncomeCategory : AddIncomeCategoryUseCase,
    val deleteCategory : DeleteCategoryUseCase
)
