package com.bima.expensetrackerapp.data.remote

import com.bima.expensetrackerapp.domain.model.Expense
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.bima.expensetrackerapp.domain.model.Transactions

@Serializable
data class TransactionDto(
    @SerialName("id")
    val id:String? = "",
    @SerialName("name")
    val name:String?,
    @SerialName("description")
    val description:String? = "",
    @SerialName("category_id")
    val categoryId:String?,
    @SerialName("user_id")
    val userId:String? = "",
    @SerialName("amount")
    val amount:Double?,
    @SerialName("date")
    val date: String?,
    @SerialName("created_at")
    val createdAt:String? = ""
)

fun TransactionDto.toTransactions() : Transactions {
    return Transactions(
        id = id,
        name = name,
        amount = amount,
        date = date,
        createdAt = createdAt
    )
}

fun TransactionDto.toExpense(): Expense {
    return Expense(
        id = id,
        name = name,
        description = description,
        categoryId = categoryId,
        userId = userId,
        amount = amount,
        date = date,
        createdAt = createdAt
    )
}