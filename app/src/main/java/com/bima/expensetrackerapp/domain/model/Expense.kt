package com.bima.expensetrackerapp.domain.model


data class Expense(
    val id:String? = "",
    val name:String?,
    val description:String? = "",
    val categoryId:String?,
    val userId:String? = "",
    val amount:Double?,
    val createdAt:String? = ""
)
