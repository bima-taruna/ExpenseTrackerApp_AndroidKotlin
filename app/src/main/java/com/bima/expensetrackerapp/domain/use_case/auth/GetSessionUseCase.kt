package com.bima.expensetrackerapp.domain.use_case.auth

import com.bima.expensetrackerapp.domain.repository.AuthenticationRepository
import io.github.jan.supabase.gotrue.user.UserSession
import javax.inject.Inject

class GetSessionUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {
    suspend fun execute(): UserSession? {
           return repository.getSession()
    }
}