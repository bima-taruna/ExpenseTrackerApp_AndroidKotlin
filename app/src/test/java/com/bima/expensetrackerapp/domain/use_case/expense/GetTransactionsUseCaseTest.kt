package com.bima.expensetrackerapp.domain.use_case.expense

import com.bima.expensetrackerapp.data.fakeRepository.FakeTransactionRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class GetTransactionsUseCaseTest {
    private lateinit var getExpensesUseCase: GetExpensesUseCase
    private lateinit var fakeExpensesRepository: FakeTransactionRepository

    @Before
    fun setUp() {
        fakeExpensesRepository = FakeTransactionRepository()
        getExpensesUseCase = GetExpensesUseCase(fakeExpensesRepository)
    }

    @Test
    fun `Get Expense List`() = runBlocking {
        val result = fakeExpensesRepository.getTransactions("income")
        assertThat(result).isEqualTo(FakeTransactionRepository().listOfExpense)
    }
}