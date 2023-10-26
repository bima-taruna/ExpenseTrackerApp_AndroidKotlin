package com.bima.expensetrackerapp.data.remote

import com.bima.expensetrackerapp.domain.model.Balance
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BalanceDto(
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("total_balance")
    val totalBalance: Int? = null,
    @SerialName("income")
    val income: Int? = null,
    @SerialName("expense")
    val expense: Int? = null,
    @SerialName("user_id")
    val userId: String? = null,
)

fun BalanceDto.toBalance() : Balance {
    return Balance(
        totalBalance = totalBalance,
        income = income,
        expense = expense
    )
}
