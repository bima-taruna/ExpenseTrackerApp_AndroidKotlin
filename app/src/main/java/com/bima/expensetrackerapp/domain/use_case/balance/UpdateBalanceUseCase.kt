package com.bima.expensetrackerapp.domain.use_case.balance

import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.domain.model.Transaction
import com.bima.expensetrackerapp.domain.repository.BalanceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class UpdateBalanceUseCase @Inject constructor(
    private val balanceRepository: BalanceRepository
) {
    suspend fun execute(userId:String, data: Int): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        val result = withContext(Dispatchers.IO) {
            balanceRepository.updateBalance(userId, data)
        }
        emit(Resource.Success(result))
    }.catch { e ->
        when (e) {
            is IOException -> emit(Resource.Error("Couldn't reach the server. Please check your internet connection."))
            else -> emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        }
    }
}