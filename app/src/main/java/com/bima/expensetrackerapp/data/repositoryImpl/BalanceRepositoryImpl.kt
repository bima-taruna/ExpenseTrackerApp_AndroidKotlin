package com.bima.expensetrackerapp.data.repositoryImpl

import com.bima.expensetrackerapp.data.remote.BalanceDto
import com.bima.expensetrackerapp.domain.repository.BalanceRepository
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject

class BalanceRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
):BalanceRepository {
    override suspend fun getBalance(): BalanceDto {
        return postgrest["balance"].select().decodeSingle()
    }

    override suspend fun updateBalance(userId: String, data: Int): Boolean {
        return try {
            postgrest.from("balance").update({
                set("total_balance", data)
            }) {
                filter {
                    eq("user_id", userId)
                }
            }
            true
        } catch (e: java.lang.Exception) {
            throw e
        }
    }
}