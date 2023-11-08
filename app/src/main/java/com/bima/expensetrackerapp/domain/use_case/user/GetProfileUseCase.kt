package com.bima.expensetrackerapp.domain.use_case.user

import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.data.remote.toUserProfile
import com.bima.expensetrackerapp.domain.model.UserProfile
import com.bima.expensetrackerapp.domain.repository.UserProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val userProfileRepository: UserProfileRepository
) {
    suspend fun execute(id: String?): Flow<Resource<UserProfile>> = flow {
        try {
            emit(Resource.Loading())
            val result = withContext(Dispatchers.IO) {
                userProfileRepository.getProfile(id).toUserProfile()
            }
            emit(Resource.Success(result))
        } catch (e:Exception) {
            emit(Resource.Error(e.localizedMessage ?: "error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("couldn't reach server, please check your internet connection" ))
        }
    }
}