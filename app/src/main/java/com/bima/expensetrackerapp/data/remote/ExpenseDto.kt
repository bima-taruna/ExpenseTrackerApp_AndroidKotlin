package com.bima.expensetrackerapp.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.bima.expensetrackerapp.domain.model.Expenses

@Serializable
data class ExpenseDto(
    @SerialName("id")
    val id:Int?,
    @SerialName("name")
    val name:String?,
    @SerialName("description")
    val description:String?,
    @SerialName("category_id")
    val categoryId:String?,
    @SerialName("images")
    val images:String?,
    @SerialName("user_id")
    val userId:String?,
    @SerialName("amount")
    val amount:Double?,
    @SerialName("created_at")
    val createdAt:String?
)

fun Expenses.toExpense() : Expenses {
    return Expenses(
        id = id,
        name = name,
        amount = amount,
        createdAt = createdAt
    )
}