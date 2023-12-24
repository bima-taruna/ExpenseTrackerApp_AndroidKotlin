package com.bima.expensetrackerapp.domain.use_case.income

import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.domain.model.Expense
import com.bima.expensetrackerapp.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class CreateIncomeUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend fun execute(input: Expense): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        val result = withContext(Dispatchers.IO) {
            repository.createTransaction(input, "income")
        }
        emit(Resource.Success(result))
    }.catch { e ->
        when (e) {
            is IOException -> emit(Resource.Error("Couldn't reach the server. Please check your internet connection."))
            else -> emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        }
    }
}