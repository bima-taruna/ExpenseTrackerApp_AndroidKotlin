package com.bima.expensetrackerapp.domain.use_case.auth

data class AuthUseCases(
    val signIn : SignInUseCase,
    val getSession : GetSessionUseCase,
    val signOut : SignOutUseCase
)
