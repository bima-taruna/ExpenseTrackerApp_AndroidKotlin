package com.bima.expensetrackerapp.domain.use_case.expense

import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.data.remote.ExpenseDto
import com.bima.expensetrackerapp.domain.repository.ExpensesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class GetExpensesUseCase @Inject constructor(
    private val expenseRepository: ExpensesRepository
) {
    suspend fun execute(): Flow<Resource<List<ExpenseDto>>> = flow {
        try {
            emit(Resource.Loading())
            val result = withContext(Dispatchers.IO) {
                expenseRepository.getTransactions("expense")
            }
            emit(Resource.Success(result))

        } catch (e:Exception) {
            emit(Resource.Error(e.localizedMessage ?: "error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("couldn't reach server, please check your internet connection" ))
        }
    }
}