package com.bima.expensetrackerapp.data.remote

import com.bima.expensetrackerapp.domain.model.Category
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    @SerialName("id")
    val id:String? = "",
    @SerialName("name")
    val name:String?,
    @SerialName("type")
    val type:String?,
    @SerialName("created_by")
    val createdBy:String? = ""
)

fun CategoryDto.toCategory() : Category {
    return Category(
        id = id,
        name = name,
        type = type,
        createdBy = createdBy
    )
}

