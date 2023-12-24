package com.bima.expensetrackerapp.domain.use_case.income

import com.bima.expensetrackerapp.domain.use_case.expense.CreateExpenseUseCase
import com.bima.expensetrackerapp.domain.use_case.expense.GetExpensesUseCase

data class IncomeUseCases(
    val getIncomes: GetIncomesUseCase,
    val createIncome:CreateIncomeUseCase
)
