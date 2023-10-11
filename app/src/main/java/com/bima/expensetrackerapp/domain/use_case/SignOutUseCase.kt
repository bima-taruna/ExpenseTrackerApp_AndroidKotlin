package com.bima.expensetrackerapp.domain.use_case

import com.bima.expensetrackerapp.domain.repository.AuthenticationRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {
    suspend fun execute() {
        repository.signOut()
    }
}