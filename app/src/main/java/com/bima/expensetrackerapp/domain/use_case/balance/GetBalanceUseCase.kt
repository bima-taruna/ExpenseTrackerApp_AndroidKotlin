package com.bima.expensetrackerapp.domain.use_case.balance

import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.data.remote.toBalance
import com.bima.expensetrackerapp.domain.model.Balance
import com.bima.expensetrackerapp.domain.repository.BalanceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class GetBalanceUseCase @Inject constructor(
    private val balanceRepository: BalanceRepository
) {
    suspend fun execute(): Flow<Resource<Balance>> = flow {
        try {
            emit(Resource.Loading())
            val result = withContext(Dispatchers.IO) {
                balanceRepository.getBalance().toBalance()
            }
            emit(Resource.Success(result))

        } catch (e:Exception) {
            emit(Resource.Error(e.localizedMessage ?: "error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("couldn't reach server, please check your internet connection" ))
        }
    }
}