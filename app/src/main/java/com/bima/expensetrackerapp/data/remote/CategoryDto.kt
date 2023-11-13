package com.bima.expensetrackerapp.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    @SerialName("id")
    val id:String?,
    @SerialName("name")
    val name:String?,
    @SerialName("type")
    val type:String?,
    @SerialName("created_by")
    val createdBy:String?
)
