package com.bima.expensetrackerapp.domain.use_case.balance

data class BalanceUseCases(
    val getBalance : GetBalanceUseCase,
    val updateBalance: UpdateBalanceUseCase
)