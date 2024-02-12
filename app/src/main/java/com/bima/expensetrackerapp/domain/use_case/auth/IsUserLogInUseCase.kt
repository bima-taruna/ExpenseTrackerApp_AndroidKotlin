package com.bima.expensetrackerapp.domain.use_case.auth

import android.content.Context
import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.domain.repository.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class IsUserLogInUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend fun execute(context: Context): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            val result = withContext(Dispatchers.IO) {
                authenticationRepository.isUserLogin(context)
            }
            emit(Resource.Success(result))
        } catch (e:Exception) {
            emit(Resource.Error(e.localizedMessage ?: "error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("couldn't reach server, please check your internet connection" ))
        }
    }
}