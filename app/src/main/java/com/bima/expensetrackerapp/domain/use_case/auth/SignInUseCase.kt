package com.bima.expensetrackerapp.domain.use_case.auth

import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.domain.repository.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend fun execute(email:String,password:String): Flow<Resource<Boolean>> = channelFlow {
        withContext(Dispatchers.IO) {
            try {
                send(Resource.Loading())
                val result = authenticationRepository.signIn(email, password)
                send(Resource.Success(result))
            } catch (e:Exception) {
                send(Resource.Error(e.localizedMessage ?: "error occured"))
            } catch (e: IOException) {
                send(Resource.Error("couldn't reach server, please check your internet connection" ))
            }
        }
    }
}