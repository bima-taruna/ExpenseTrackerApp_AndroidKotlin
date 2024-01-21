package com.bima.expensetrackerapp.domain.use_case.income

import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.data.remote.toTransaction
import com.bima.expensetrackerapp.domain.model.Transaction
import com.bima.expensetrackerapp.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class GetIncomeByIdUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend fun execute(id:String): Flow<Resource<Transaction>> = flow {
        try {
            emit(Resource.Loading())
            val result = withContext(Dispatchers.IO) {
                transactionRepository.getTransactionById("income", id).toTransaction()
            }
            emit(Resource.Success(result))
        }  catch (e:Exception) {
            emit(Resource.Error(e.localizedMessage ?: "error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("couldn't reach server, please check your internet connection" ))
        }
    }
}