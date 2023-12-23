package com.bima.expensetrackerapp.domain.use_case.expense

import com.bima.expensetrackerapp.data.fakeRepository.FakeExpensesRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking

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
        val result = fakeExpensesRepository.getTransactions()
        assertThat(result).isEqualTo(FakeExpensesRepository().listOfExpense)
    }
}