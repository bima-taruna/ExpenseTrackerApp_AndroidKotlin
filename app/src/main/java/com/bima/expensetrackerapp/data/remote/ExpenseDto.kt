package com.bima.expensetrackerapp.data.remote

import com.bima.expensetrackerapp.domain.model.Expense
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.bima.expensetrackerapp.domain.model.Expenses

@Serializable
data class ExpenseDto(
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
    @SerialName("created_at")
    val createdAt:String? = ""
)

fun ExpenseDto.toExpenses() : Expenses {
    return Expenses(
        id = id,
        name = name,
        amount = amount,
        createdAt = createdAt
    )
}

fun ExpenseDto.toExpense(): Expense {
    return Expense(
        id = id,
        name = name,
        description = description,
        categoryId = categoryId,
        userId = userId,
        amount = amount,
        createdAt = createdAt
    )
}