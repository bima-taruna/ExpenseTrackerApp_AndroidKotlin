package com.bima.expensetrackerapp.domain.use_case.expense

import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.data.fakeRepository.FakeExpensesRepository
import com.bima.expensetrackerapp.data.remote.ExpenseDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class GetExpensesUseCaseTest {
    private lateinit var getExpensesUseCase: GetExpensesUseCase
    private lateinit var fakeExpensesRepository: FakeExpensesRepository

    @Before
    fun setUp() {
        fakeExpensesRepository = FakeExpensesRepository()
        getExpensesUseCase = GetExpensesUseCase(fakeExpensesRepository)
    }

    @Test
    fun `Get Expense List`() = runBlocking {
        val result = fakeExpensesRepository.getExpenses()
        assertThat(result).isEqualTo(FakeExpensesRepository().listOfExpense)
    }
}