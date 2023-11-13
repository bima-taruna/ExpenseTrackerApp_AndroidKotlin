package com.bima.expensetrackerapp.domain.model

import java.util.Date

data class Expenses(
    val id:String,
    val name:String,
    val amount:String,
    val createdAt:Date
)
