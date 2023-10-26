package com.bima.expensetrackerapp.domain.repository

import com.bima.expensetrackerapp.data.remote.BalanceDto

interface BalanceRepository {
    suspend fun getBalance():BalanceDto
}